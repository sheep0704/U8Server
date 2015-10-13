package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.EncryptUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 拇指玩支付回调通知接口
 * Created by ant on 2015/4/25.
 */
@Controller
@Namespace("/pay/muzhiwan")
public class MuZhiWanPayCallbackAction extends UActionSupport{

    private String appkey      ;                    //字符串     游戏唯一标记
    private String orderID     ;                    //字符串     订单唯一标记
    private String productName ;                    //字符串     商品名称
    private String productDesc ;                    //字符串     商品描述
    private String productID   ;                    //字符串     商品ID
    private String money       ;                    //字符串     金额，为整形，无小数点，元为单位
    private String uid         ;                    //字符串     充值用户ID
    private String extern      ;                    //字符串     扩展域
    private String sign        ;                    //字符串     签名，签名规则如下：

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long localOrderID = Long.parseLong(extern);

            UOrder order = orderManager.getOrder(localOrderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false, "notifyId 错误");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(false, "该订单已经被处理,或者CP订单号重复");
                return;
            }

            int orderMoney = Integer.valueOf(money) * 100;      //转换为分


            if(order.getMoney() != orderMoney){
                Log.e("订单金额不一致! local orderID:"+localOrderID+"; money returned:"+money+"; order money:"+order.getMoney());
                this.renderState(false, "订单金额不一致");
                return;
            }

            if(isValid(order.getChannel())){
                order.setChannelOrderID(orderID);
                order.setState(PayState.STATE_SUC);
                orderManager.saveOrder(order);
                SendAgent.sendCallbackToServer(this.orderManager, order);
                this.renderState(true, "");
            }else{
                order.setChannelOrderID(orderID);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
                this.renderState(false, "sign 错误");
            }

        }catch (Exception e){
            e.printStackTrace();
            try {
                this.renderState(false, "未知错误");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean isValid(UChannel channel){

        StringBuilder sb = new StringBuilder();
        sb.append(channel.getCpAppKey()).append(orderID).append(productName)
                .append(productDesc).append(productID).append(money).append(uid)
                .append(extern).append(channel.getCpAppSecret());

        String vCode = EncryptUtils.md5(sb.toString()).toLowerCase();

        return vCode.equals(this.sign);

    }

    private void renderState(boolean suc, String msg) throws IOException {

        if(suc){
            this.response.getWriter().write("SUCCESS");
        }

    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getExtern() {
        return extern;
    }

    public void setExtern(String extern) {
        this.extern = extern;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

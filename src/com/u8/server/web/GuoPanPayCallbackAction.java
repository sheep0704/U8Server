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
 * 叉叉助手 支付回调
 * Created by ant on 2015/9/14.
 */
@Controller
@Namespace("/pay/guopan")
public class GuoPanPayCallbackAction extends UActionSupport{

    private String trade_no;            //果盘唯一订单号
    private String serialNumber;        //游戏方订单序列号
    private String money;               //消费金额。单位是元，精确到分，如10.00
    private String status;              //状态；0=失败；1=成功；2=失败，原因是余额不足。
    private String t;                   //时间戳
    private String sign;                //加密串 sign=md5(serialNumber +money+status+t+SERVER_KEY) 是五个变量值拼接后经md5后的值，其中SERVER_KEY在果盘开放平台上获得。
    private String appid;
    private String item_id;
    private String item_price;
    private String item_count;
    private String reserved;            //扩展参数，SDK发起支付时有传递，则这里会回传。

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long orderID = Long.parseLong(serialNumber);

            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false);
                return;
            }

            UChannel channel = order.getChannel();
            if(channel == null){

                this.renderState(false);
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE) {
                Log.d("The state of the order is complete. The state is " + order.getState());
                this.renderState(true);
                return;
            }

            if(!isSignOK(channel)){
                Log.d("The sign verify failed.sign:%s;appKey:%s;orderID:%s", sign, channel.getCpPayKey(), serialNumber);
                this.renderState(true);
                return;
            }

            if("1".equals(this.status)){

                float money = Float.parseFloat(this.money);
                int moneyInt = (int)(money * 100);  //以分为单位

                order.setMoney(moneyInt);
                order.setChannelOrderID(this.trade_no);
                order.setState(PayState.STATE_SUC);

                orderManager.saveOrder(order);

                SendAgent.sendCallbackToServer(this.orderManager, order);

            }else{
                order.setChannelOrderID(this.trade_no);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
            }


        }catch(Exception e){
            try {
                renderState(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private boolean isSignOK(UChannel channel){
        StringBuilder sb = new StringBuilder();
        sb.append(this.serialNumber)
                .append(this.money)
                .append(this.status)
                .append(this.t)
                .append(channel.getCpPayKey());

        String md5 = EncryptUtils.md5(sb.toString()).toLowerCase();

        return md5.equals(this.sign);
    }

    private void renderState(boolean suc) throws IOException {

        String res = "success";
        if(!suc){
            res = "fail";
        }

        this.response.getWriter().write(res);
    }


    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}

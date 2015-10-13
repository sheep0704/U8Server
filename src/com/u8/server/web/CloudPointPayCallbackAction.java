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
 * 友游支付回调通知接口
 * Created by ant on 2015/4/28.
 */
@Controller
@Namespace("/pay/cloudpoint")
public class CloudPointPayCallbackAction extends UActionSupport{

    private String uid  		;		//用户 ID
    private String order_sn  	;		//友游内部流水号
    private String order_number ; 		//第三方游戏订单号
    private String amount  		;		//交易金额（单位：元）
    private String time  		;		//发起通知请求时间
    private String status  		;		//交易状态（只有 1 为交易成功）
    private String sign  		;		//请求签名

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long localOrderID = Long.parseLong(order_number);

            UOrder order = orderManager.getOrder(localOrderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false, "notifyId 错误");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(true, "该订单已经被处理,或者CP订单号重复");
                return;
            }

            if(!"1".equals(this.status)){
                Log.e("平台支付失败 local orderID:"+localOrderID+";order id:" + order_sn);
                this.renderState(false, "支付中心返回的结果是支付失败");
                return;
            }

            if(isValid(order.getChannel())){
                order.setChannelOrderID(order_sn);
                order.setState(PayState.STATE_SUC);
                orderManager.saveOrder(order);
                SendAgent.sendCallbackToServer(this.orderManager, order);
                this.renderState(true, "");
            }else{
                order.setChannelOrderID(order_sn);
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
        sb.append("amount=").append(amount).append("&")
                .append("order_number=").append(order_number).append("&")
                .append("order_sn=").append(order_sn).append("&")
                .append("status=").append(status).append("&")
                .append("time=").append(time).append("&")
                .append("uid=").append(uid).append("@").append(channel.getCpAppKey());

        String vCode = EncryptUtils.md5(sb.toString()).toLowerCase();

        return vCode.equals(this.sign);

    }

    private void renderState(boolean suc, String msg) throws IOException {

        if(suc){
            this.response.getWriter().write("SUCCESS");
        }else{
            this.response.getWriter().write("FAILURE");
        }

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

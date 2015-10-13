package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.anzhi.Des3Util;
import com.u8.server.service.UChannelManager;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.JsonUtils;
import com.u8.server.web.SendAgent;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 安智支付回调
 * Created by ant on 2015/4/29.
 */
@Controller
@Namespace("/pay/anzhi")
public class AnzhiPayCallbackAction extends UActionSupport{

    private String data;
    private int u8ChannelID;


    @Autowired
    private UOrderManager orderManager;

    @Autowired
    private UChannelManager channelManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            UChannel channel = channelManager.queryChannel(this.u8ChannelID);
            if(channel == null){
                Log.e("The channel is not exists. channelID:"+this.u8ChannelID+";data:"+this.data);
                this.renderState(false, "data error");
                return;
            }

            String json = Des3Util.decrypt(this.data, channel.getCpAppSecret());
            Log.d("The anzhi data is "+json);

            AnzhiPayContent data = (AnzhiPayContent) JsonUtils.decodeJson(json, AnzhiPayContent.class);

            if(data == null){
                Log.e("The content parse error...");
                this.renderState(false, "data error");
                return;
            }

            long localOrderID = Long.parseLong(data.getCpInfo());

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

            if(!"1".equals(data.getCode())){
                Log.e("平台支付失败 local orderID:"+localOrderID+"; order id:" + data.getOrderId());
                this.renderState(false, "支付中心返回的结果是支付失败");
                return;
            }

            order.setChannelOrderID(data.getOrderId());
            order.setMoney(Integer.valueOf(data.getOrderAccount()));
            order.setState(PayState.STATE_SUC);
            orderManager.saveOrder(order);
            SendAgent.sendCallbackToServer(this.orderManager, order);
            this.renderState(true, "");

        }catch (Exception e){
            e.printStackTrace();
            try {
                this.renderState(false, "未知错误");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void renderState(boolean suc, String msg) throws IOException {

        if(suc){
            this.response.getWriter().write("success");
        }else{
            this.response.getWriter().write("failure");
        }

    }

    public static class AnzhiPayContent{

        private String payAmount	;
        private String uid			;
        private String notifyTime	;
        private String cpInfo		;
        private String memo			;
        private String orderAmount	;
        private String orderAccount	;
        private String code			;
        private String orderTime	;
        private String msg			;
        private String orderId		;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNotifyTime() {
            return notifyTime;
        }

        public void setNotifyTime(String notifyTime) {
            this.notifyTime = notifyTime;
        }

        public String getCpInfo() {
            return cpInfo;
        }

        public void setCpInfo(String cpInfo) {
            this.cpInfo = cpInfo;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(String orderAmount) {
            this.orderAmount = orderAmount;
        }

        public String getOrderAccount() {
            return orderAccount;
        }

        public void setOrderAccount(String orderAccount) {
            this.orderAccount = orderAccount;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getU8ChannelID() {
        return u8ChannelID;
    }

    public void setU8ChannelID(int u8ChannelID) {
        this.u8ChannelID = u8ChannelID;
    }
}

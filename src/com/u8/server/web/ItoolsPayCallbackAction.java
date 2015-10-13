package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.anzhi.Des3Util;
import com.u8.server.sdk.itools.RSASignature;
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
 * ITools支付回调
 * Created by ant on 2015/8/10.
 */

@Controller
@Namespace("/pay/itools")
public class ItoolsPayCallbackAction extends UActionSupport{

    private String notify_data;
    private String sign;
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
                Log.e("The channel is not exists. channelID:" + this.u8ChannelID + ";data:" + this.notify_data);
                return;
            }

            String json = RSASignature.decrypt(this.notify_data, channel.getCpPayKey());
            Log.d("The itools data is "+json);

            ItoolsPayContent data = (ItoolsPayContent) JsonUtils.decodeJson(json, ItoolsPayContent.class);

            if(data == null){
                Log.e("The content parse error...");
                this.renderState(false, "data error");
                return;
            }

            long localOrderID = Long.parseLong(data.getOrder_id_com());

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

            if(!"success".equals(data.getResult())){
                Log.e("平台支付失败 local orderID:"+localOrderID+"; order id:" + data.getOrder_id());
                this.renderState(false, "支付中心返回的结果是支付失败");
                return;
            }

            order.setChannelOrderID(data.getOrder_id());
            order.setMoney((int)(Float.valueOf(data.getAccount()) * 100));
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
            this.response.getWriter().write("fail");
        }

    }


    public static class ItoolsPayContent{

        private String order_id_com;
        private String user_id;
        private String amount;
        private String account;
        private String order_id;
        private String result;

        public String getOrder_id_com() {
            return order_id_com;
        }

        public void setOrder_id_com(String order_id_com) {
            this.order_id_com = order_id_com;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public String getNotify_data() {
        return notify_data;
    }

    public void setNotify_data(String notify_data) {
        this.notify_data = notify_data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getU8ChannelID() {
        return u8ChannelID;
    }

    public void setU8ChannelID(int u8ChannelID) {
        this.u8ChannelID = u8ChannelID;
    }
}

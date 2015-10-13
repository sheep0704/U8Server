package com.u8.server.web;

import com.lenovo.pay.sign.CpTransSyncSignValid;
import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.kuaiyong.kuaiyong.Base64;
import com.u8.server.sdk.kuaiyong.kuaiyong.RSAEncrypt;
import com.u8.server.sdk.kuaiyong.kuaiyong.RSASignature;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.HttpUtils;
import com.u8.server.utils.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;

/**
 * 快用支付回调
 * Created by ant on 2015/9/22.
 */

@Controller
@Namespace("/pay/kuaiyong")

public class KuaiYongPayCallbackAction extends UActionSupport{

    private String notify_data;
    private String orderid;
    private String dealseq;
    private String uid;
    private String subject;
    private String v;
    private String sign;

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{


            long localOrderID = Long.parseLong(dealseq);

            UOrder order = orderManager.getOrder(localOrderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.orderID:"+dealseq);
                this.renderState(false, "notifyId 错误");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The order is already completed.local orderID:%s; orderID:%s", localOrderID, orderid);
                this.renderState(true, "该订单已经被处理,或者CP订单号重复");
                return;
            }

            if(!isValid(order.getChannel())){
                Log.d("The sign is not valid, sign:%s; orderID:%s", sign, dealseq);
                this.renderState(false , "签名错误");
                return;
            }

            RSAEncrypt encrypt = new RSAEncrypt();
            encrypt.loadPublicKey(order.getChannel().getCpPayKey());
            byte[] dcDataStr = Base64.decode(notify_data);
            byte[] plainData = encrypt.decrypt(encrypt.getPublicKey(), dcDataStr);
            //获取到加密通告信息
            String notifyDataStr = new String(plainData, "UTF-8");

            Map<String, String> notifyData = HttpUtils.parseUrlParams(notifyDataStr);

            String feeStr = notifyData.containsKey("fee") ? notifyData.get("fee") : null;
            String payresultStr = notifyData.containsKey("payresult") ? notifyData.get("payresult") : null;

            int fee = (int)(Float.valueOf(feeStr) * 100);
            int payresult = Integer.valueOf(payresultStr);


            if(order.getMoney() != fee){

                Log.e("订单金额不一致, local orderID:%s;local money:%s;money:%s", localOrderID, fee, order.getMoney());
            }

            if(payresult != 0){

                Log.e("平台支付失败 local orderID:%s; pay result:%s", localOrderID, payresult);
                this.renderState(false, "联想支付中心返回的结果是支付失败");
                return;
            }

            order.setChannelOrderID(orderid);
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

    private boolean isValid(UChannel channel){

        StringBuilder sb = new StringBuilder();
        sb.append("dealseq=").append(dealseq).append("&")
                .append("notify_data=").append(notify_data).append("&")
                .append("orderid=").append(orderid).append("&")
                .append("subject=").append(subject).append("&")
                .append("uid=").append(uid).append("&")
                .append("v=").append(v);


        return RSASignature.doCheck(sb.toString(), sign, channel.getCpPayKey(), "utf-8");

    }

    private void renderState(boolean suc, String msg) throws IOException {

        if(suc){
            this.response.getWriter().write("success");
        }else{
            this.response.getWriter().write("failed");
        }

    }

    public String getNotify_data() {
        return notify_data;
    }

    public void setNotify_data(String notify_data) {
        this.notify_data = notify_data;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getDealseq() {
        return dealseq;
    }

    public void setDealseq(String dealseq) {
        this.dealseq = dealseq;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

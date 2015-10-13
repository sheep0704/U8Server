package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.EncryptUtils;
import org.apache.http.util.TextUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * VIVO支付回调地址
 * Created by ant on 2015/4/24.
 */

@Controller
@Namespace("/pay/vivo")
public class VivoPayCallbackAction extends UActionSupport{

    private String respCode		;					//响应码	200
    private String respMsg		;					//响应信息	交易完成
    private String signMethod	;					//签名方法	对关键信息进行签名的算法名称：MD5
    private String signature	;					//签名信息	对关键信息签名后得到的字符串1，用于商户验签签名规则请参考附录 2.6.3 消息签名
    private String tradeType	;					//交易种类	目前固定01
    private String tradeStatus	;					//交易状态	0000，代表支付成功
    private String cpId			;					//Cp-id	定长20位数字，由vivo分发的唯一识别码
    private String appId		;					//appId	应用ID
    private String uid			;					//用户在vivo这边的唯一标识
    private String cpOrderNumber;					//商户自定义的订单号	商户自定义，最长 64 位字母、数字和下划线组成
    private String orderNumber	;					//交易流水号	vivo订单号
    private String orderAmount	;					//交易金额	单位：分，币种：人民币，为长整型，如：101，10000
    private String extInfo		;					//商户透传参数	64位
    private String payTime		;					//交易时间	yyyyMMddHHmmss

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long orderID = Long.parseLong(cpOrderNumber);

            UOrder order = orderManager.getOrder(orderID);

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

            if(this.respCode.equals("200") && this.tradeStatus.equals("0000")){
                if(isValid(order.getChannel())){
                    order.setMoney(Integer.valueOf(orderAmount));
                    order.setChannelOrderID(orderNumber);
                    order.setState(PayState.STATE_SUC);
                    orderManager.saveOrder(order);
                    SendAgent.sendCallbackToServer(this.orderManager, order);
                    this.renderState(true, "");
                }else{
                    order.setChannelOrderID(orderNumber);
                    order.setState(PayState.STATE_FAILED);
                    orderManager.saveOrder(order);
                    this.renderState(false, "sign 错误");
                }
            }else{

                this.renderState(false, "支付失败");
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
        sb.append("appId=").append(appId).append("&")
                .append("cpId=").append(channel.getCpID()).append("&")
                .append("cpOrderNumber=").append(cpOrderNumber).append("&");

        if(!TextUtils.isEmpty(extInfo)){
            sb.append("extInfo=").append(extInfo).append("&");
        }

        sb.append("orderAmount=").append(orderAmount).append("&")
                .append("orderNumber=").append(orderNumber).append("&")
                .append("payTime=").append(payTime).append("&")
                .append("respCode=").append(respCode).append("&");

        if(!TextUtils.isEmpty(respMsg)){
            sb.append("respMsg=").append(respMsg).append("&");
        }

        sb.append("tradeStatus=").append(tradeStatus).append("&")
                .append("tradeType=").append(tradeType).append("&")
                .append("uid=").append(uid).append("&");

        sb.append(EncryptUtils.md5(channel.getCpAppKey()).toLowerCase());

        String vCode = EncryptUtils.md5(sb.toString()).toLowerCase();

        return vCode.equals(this.signature);

    }

    private void renderState(boolean suc, String msg) throws IOException {

        StringBuilder sb = new StringBuilder();


        sb.append("result=").append(suc ? "OK" : "FAIL");
        sb.append("&").append("resultMsg=").append(msg);

        Log.d("The result to sdk is "+sb.toString());

        if(suc){
            this.response.setStatus(200);
        }else{
            this.response.setStatus(403);
        }
        this.response.getWriter().write(sb.toString());


    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCpOrderNumber() {
        return cpOrderNumber;
    }

    public void setCpOrderNumber(String cpOrderNumber) {
        this.cpOrderNumber = cpOrderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}

package com.u8.server.data;

import net.sf.json.JSONObject;

import javax.persistence.*;

/**
 * 渠道商对象(比如UC，当乐。。。)
 */
@Entity
@Table(name = "uchannelmaster")
public class UChannelMaster {

    @Id
    private Integer masterID;           //渠道商ID号
    private String sdkName;         //当前渠道商所使用的sdk名称
    private String masterName;      //当前渠道商名称
    private String nameSuffix;      //用户名渠道商后缀
    private String authUrl;         //当前SDK登录认证地址
    private String payCallbackUrl;  //当前SDK支付通知回调地址
    private String verifyClass;     //当前SDK的验证处理类的全类名
    private String orderUrl;        //SDK订单号获取地址，没有则为空

    public JSONObject toJSON(){

        JSONObject json = new JSONObject();
        json.put("masterID", masterID);
        json.put("sdkName", sdkName);
        json.put("masterName", masterName);
        json.put("nameSuffix", nameSuffix);
        json.put("authUrl", authUrl);
        json.put("payCallbackUrl", payCallbackUrl);
        json.put("verifyClass", verifyClass);
        json.put("orderUrl", orderUrl);

        return json;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Integer getMasterID() {
        return masterID;
    }

    public void setMasterID(Integer masterID) {
        this.masterID = masterID;
    }

    public String getSdkName() {
        return sdkName;
    }

    public void setSdkName(String sdkName) {
        this.sdkName = sdkName;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public String getVerifyClass() {
        return verifyClass;
    }

    public void setVerifyClass(String verifyClass) {
        this.verifyClass = verifyClass;
    }

    public String getOrderUrl() {
        return orderUrl;
    }

    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

}

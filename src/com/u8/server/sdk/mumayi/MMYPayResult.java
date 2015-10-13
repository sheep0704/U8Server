package com.u8.server.sdk.mumayi;

/**
 * Created by ant on 2015/9/14.
 */
public class MMYPayResult {

    private String uid;
    private String orderID;
    private String productName;
    private String productPrice;
    private String productDesc;
    private String orderTime;
    private String tradeSign;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTradeSign() {
        return tradeSign;
    }

    public void setTradeSign(String tradeSign) {
        this.tradeSign = tradeSign;
    }
}

package com.u8.server.sdk.baidu;

import java.util.Date;

/**
 * Created by ant on 2015/2/28.
 */
public class BaiduPayResult {

    private long UID;
    private String MerchandiseName;
    private double OrderMoney;
    private Date StartDateTime;
    private Date BankDateTime;
    private int OrderStatus;
    private String StatusMsg;
    private String ExtInfo;

    public long getUID() {
        return UID;
    }

    public void setUID(long UID) {
        this.UID = UID;
    }

    public String getMerchandiseName() {
        return MerchandiseName;
    }

    public void setMerchandiseName(String merchandiseName) {
        MerchandiseName = merchandiseName;
    }

    public double getOrderMoney() {
        return OrderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        OrderMoney = orderMoney;
    }

    public Date getStartDateTime() {
        return StartDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        StartDateTime = startDateTime;
    }

    public Date getBankDateTime() {
        return BankDateTime;
    }

    public void setBankDateTime(Date bankDateTime) {
        BankDateTime = bankDateTime;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getStatusMsg() {
        return StatusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        StatusMsg = statusMsg;
    }

    public String getExtInfo() {
        return ExtInfo;
    }

    public void setExtInfo(String extInfo) {
        ExtInfo = extInfo;
    }
}

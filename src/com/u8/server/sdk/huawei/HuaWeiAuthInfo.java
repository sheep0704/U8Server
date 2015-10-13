package com.u8.server.sdk.huawei;

/**
 * Created by ant on 2015/4/27.
 */
public class HuaWeiAuthInfo {

    private String userID;
    private String userName;
    private String languageCode;
    private String userState;
    private String userValidStatus;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserValidStatus() {
        return userValidStatus;
    }

    public void setUserValidStatus(String userValidStatus) {
        this.userValidStatus = userValidStatus;
    }
}

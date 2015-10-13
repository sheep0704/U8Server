package com.u8.server.sdk;

/**
 * SDK 验证结果
 */
public class SDKVerifyResult {

    private boolean success;

    private String userID;

    private String userName;

    private String nickName;

    private String extension;

    public SDKVerifyResult(){

    }

    public SDKVerifyResult(boolean suc, String userID, String userName, String nickName){
        this.success = suc;
        this.userID = userID;
        this.userName = userName;
        this.nickName = nickName;
        this.extension = "";
    }


    public SDKVerifyResult(boolean suc, String userID, String userName, String nickName, String ext){
        this.success = suc;
        this.userID = userID;
        this.userName = userName;
        this.nickName = nickName;
        this.extension = ext;
    }

    public SDKVerifyResult(boolean suc){
        this.success = suc;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}

package com.u8.server.sdk.baidu;

/**
 * Created by ant on 2015/2/28.
 */
public class BaiduResponse {

    private int AppID;

    private int ResultCode;

    private String ResultMsg;

    private String Sign;

    private String Content;

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String resultMsg) {
        ResultMsg = resultMsg;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}

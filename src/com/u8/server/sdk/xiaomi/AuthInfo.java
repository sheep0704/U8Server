package com.u8.server.sdk.xiaomi;

/**
 * Created by ant on 2015/4/21.
 */
public class AuthInfo {

    private int errcode;
    private String errMsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}

package com.u8.server.sdk.coolpad;

/**
 * Created by ant on 2015/4/8.
 */
public class CoolPadUserInfo {

    private String rtn_code;
    private String sex;
    private String nickname;
    private String brithday;
    private String highDefUrl;
    private String headIconUrl;

    public String getRtn_code() {
        return rtn_code;
    }

    public void setRtn_code(String rtn_code) {
        this.rtn_code = rtn_code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getHighDefUrl() {
        return highDefUrl;
    }

    public void setHighDefUrl(String highDefUrl) {
        this.highDefUrl = highDefUrl;
    }

    public String getHeadIconUrl() {
        return headIconUrl;
    }

    public void setHeadIconUrl(String headIconUrl) {
        this.headIconUrl = headIconUrl;
    }
}

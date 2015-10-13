package com.u8.server.sdk.paojiao;

/**
 *
 * Created by ant on 2015/4/30.
 */
public class PaoJiaoAuthInfo {

    private String msg;
    private int code;
    private PaoJiaoUserInfo data;


    public static class PaoJiaoUserInfo{
        private String createdTime;
        private String niceName;
        private String token;
        private String uid;
        private String userName;
        private String avatar;

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getNiceName() {
            return niceName;
        }

        public void setNiceName(String niceName) {
            this.niceName = niceName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PaoJiaoUserInfo getData() {
        return data;
    }

    public void setData(PaoJiaoUserInfo data) {
        this.data = data;
    }
}

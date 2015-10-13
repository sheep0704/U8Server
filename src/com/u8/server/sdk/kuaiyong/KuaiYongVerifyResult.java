package com.u8.server.sdk.kuaiyong;

/**
 * 快用登录认证结果
 * Created by ant on 2015/9/22.
 */
public class KuaiYongVerifyResult {

    private int code;
    private String msg;

    private UserData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public static class UserData{
        private String guid;
        private String username;
        private String refer_type;
        private String refer_name;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRefer_type() {
            return refer_type;
        }

        public void setRefer_type(String refer_type) {
            this.refer_type = refer_type;
        }

        public String getRefer_name() {
            return refer_name;
        }

        public void setRefer_name(String refer_name) {
            this.refer_name = refer_name;
        }
    }

}

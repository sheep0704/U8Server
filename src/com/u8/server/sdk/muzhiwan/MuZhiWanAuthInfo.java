package com.u8.server.sdk.muzhiwan;

/**
 * Created by ant on 2015/4/25.
 */
public class MuZhiWanAuthInfo {

    private int code;
    private String msg;

    private MuZhiWanUser user;

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

    public MuZhiWanUser getUser() {
        return user;
    }

    public void setUser(MuZhiWanUser user) {
        this.user = user;
    }

    public static class MuZhiWanUser{
        private String username;
        private String uid;
        private int sex;
        private String mail;
        private String icon;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

}

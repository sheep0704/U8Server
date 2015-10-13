package com.u8.server.sdk.appchina;

/**
 * Created by ant on 2015/4/28.
 */
public class AppChinaAuthInfo {

    private int status;
    private String message;
    private AppChinaUserInfo data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppChinaUserInfo getData() {
        return data;
    }

    public void setData(AppChinaUserInfo data) {
        this.data = data;
    }

    public static class AppChinaUserInfo{
        private String nick_name;
        private String user_name;
        private String phone;
        private String avatar_url;
        private String email;
        private String ticket;
        private String state;
        private String user_id;
        private boolean actived;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public boolean isActived() {
            return actived;
        }

        public void setActived(boolean actived) {
            this.actived = actived;
        }
    }

}

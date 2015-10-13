package com.u8.server.data;

import net.sf.json.JSONObject;

/**
 * Token对象
 */
public class UToken {

    private String token;
    private long time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public JSONObject toJson(){

        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("timestamp", time);

        return json;    }
}

package com.u8.server.sdk.uc;

import org.apache.commons.lang.StringUtils;

/**
 * Created by ant on 2014/12/12.
 */
public class IP {
    private String ip;
    private String port;
    private String isp;

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getIsp() {
        return isp;
    }
    public void setIsp(String isp) {
        this.isp = isp;
    }
    public String toString(){
        if(StringUtils.isEmpty(port)){
            return ip;
        }else{
            return ip+":"+port;
        }

    }
}
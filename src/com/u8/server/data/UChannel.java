package com.u8.server.data;

import com.u8.server.cache.CacheManager;
import net.sf.json.JSONObject;

import javax.persistence.*;

/**
 * 渠道对象(每个游戏对某个渠道)
 */

@Entity
@Table(name = "uchannel")
public class UChannel {

    @Id
    private Integer channelID;              //渠道ID 和客户端一致
    private int appID;                  //游戏ID
    private int masterID;               //渠道商ID

    private String cpID;                //渠道分配给游戏的cpID
    private String cpAppID;             //渠道分配给游戏的appID
    private String cpAppKey;            //渠道分配给游戏的AppKey
    private String cpAppSecret;         //渠道分配给游戏的AppSecret

    private String cpPayID;             //渠道分配给游戏的支付ID
    private String cpPayKey;            //渠道分配给游戏的支付公钥
    private String cpPayPriKey;         //渠道分配给游戏的支付私钥

    private String cpConfig;            //部分渠道可能有特殊配置信息，设置在该字段中

    public UChannelMaster getMaster(){

        return CacheManager.getInstance().getMaster(masterID);
    }

    public UGame getGame(){

        return CacheManager.getInstance().getGame(appID);
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("channelID", channelID);
        json.put("appID", appID);

        UGame game = getGame();

        json.put("appName", game == null ? "" : game.getName());
        json.put("masterID", masterID);

        UChannelMaster master = getMaster();
        json.put("masterName", master == null ? "" : master.getMasterName());
        json.put("cpID", cpID);
        json.put("cpAppID", cpAppID);
        json.put("cpAppKey", cpAppKey);
        json.put("cpAppSecret", cpAppSecret);
        json.put("cpPayID", cpPayID);
        json.put("cpPayKey", cpPayKey);
        json.put("cpPayPriKey", cpPayPriKey);
        json.put("cpConfig",cpConfig);

        return json;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public int getAppID() {
        return appID;
    }

    public void setAppID(int appID) {
        this.appID = appID;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public String getCpID() {
        return cpID;
    }

    public void setCpID(String cpID) {
        this.cpID = cpID;
    }

    public String getCpAppID() {
        return cpAppID;
    }

    public void setCpAppID(String cpAppID) {
        this.cpAppID = cpAppID;
    }

    public String getCpAppKey() {
        return cpAppKey;
    }

    public void setCpAppKey(String cpAppKey) {
        this.cpAppKey = cpAppKey;
    }

    public String getCpPayKey() {
        return cpPayKey;
    }

    public void setCpPayKey(String cpPayKey) {
        this.cpPayKey = cpPayKey;
    }

    public String getCpAppSecret() {
        return cpAppSecret;
    }

    public void setCpAppSecret(String cpAppSecret) {
        this.cpAppSecret = cpAppSecret;
    }

    public String getCpConfig() {
        return cpConfig;
    }

    public void setCpConfig(String cpConfig) {
        this.cpConfig = cpConfig;
    }

    public String getCpPayID() {
        return cpPayID;
    }

    public void setCpPayID(String cpPayID) {
        this.cpPayID = cpPayID;
    }

    public String getCpPayPriKey() {
        return cpPayPriKey;
    }

    public void setCpPayPriKey(String cpPayPriKey) {
        this.cpPayPriKey = cpPayPriKey;
    }


}

package com.u8.server.sdk.uc;

/**
 * Created by ant on 2014/12/12.
 */
public class ConfigHelper {

    /**
     * 用户会话验证
     */
    public static final String VERIFYSESSION = "account.verifySession";

    /*
     * 登录状态同步,游戏官方账号登录
     */
    public static final String BINDCREATE = "ucid.bind.create";

    /**
     * 扩展数据提交接口
     */
    public static final String GAMEDATA = "ucid.game.gameData";

    /*
     * 服务器IP列表
     */
    public static final String IPLIST = "system.getIPList";

    //public static final String ServerHost = "http://server.g.uc.cn/";
    public static final String ServerHost = "http://server.test4.g.uc.cn/";
    public static final String AUTH_URL = "cp/account.verifySession";
    public static final String IPLIST_URL = "ss";


    public static String getServiceUrl(String service){
        if(VERIFYSESSION.equals(service)){
            return ServerHost + AUTH_URL;
        }

        return ServerHost + IPLIST_URL;
    }
}

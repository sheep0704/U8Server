package com.u8.server.sdk;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;

/**
 * SDK操作脚本
 * 第三方登录认证的校验
 *
 * 关于项目中编码问题
 * tomcat中sever.xml中Connector中需要设置编码为utf-8
 * web.xml中
 * struts.xml中
 * 都设置为utf-8
 *
 */
public interface ISDKScript {

    public void verify(UChannel channel, String extension, ISDKVerifyListener callback);

    public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback);

}

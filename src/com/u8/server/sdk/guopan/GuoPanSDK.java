package com.u8.server.sdk.guopan;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 叉叉助手 还没有验证通过
 * Created by ant on 2015/4/30.
 */
public class GuoPanSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {
        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String sid = json.getString("uin");
            String token = json.getString("token");
            final String name = json.getString("username");
            String t = (System.currentTimeMillis() / 1000) + "";
            Map<String,String> params = new HashMap<String, String>();
            params.put("game_uin", sid);
            params.put("appid", channel.getCpAppID());
            params.put("token", token);
            params.put("t", t);

            StringBuilder sb = new StringBuilder();
            sb.append(sid).append(channel.getCpAppID()).append(t).append(channel.getCpAppSecret());

            String sign = EncryptUtils.md5(sb.toString());
            params.put("sign", sign);


            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        if("true".equals(result)){

                            callback.onSuccess(new SDKVerifyResult(true, sid, name, ""));
                            return;

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the get result is " + result);
                }

                @Override
                public void failed(String err) {
                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + err);
                }

            });


        }catch (Exception e){
            e.printStackTrace();
            callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed. the exception is "+e.getMessage());
        }
    }

    @Override
    public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback) {
        if(callback != null){
            callback.onSuccess("");
        }
    }
}

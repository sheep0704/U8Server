package com.u8.server.sdk.xiaomi;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.HmacSHA1Encryption;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ant on 2015/4/21.
 */
public class XiaoMiSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String sid = json.getString("sid");
            final String session = json.getString("token");

            Map<String, String> params = new HashMap<String, String>();
            params.put("appId", channel.getCpAppID());
            params.put("session", session);
            params.put("uid", sid);

            StringBuilder sb = new StringBuilder();
            sb.append("appId=").append(channel.getCpAppID()).append("&")
                    .append("session=").append(session).append("&")
                    .append("uid=").append(sid);

            String signature = HmacSHA1Encryption.HmacSHA1Encrypt(sb.toString(), channel.getCpAppSecret());


            params.put("signature", signature);
            UHttpAgent.getInstance().get(channel.getMaster().getAuthUrl(), params, new UHttpFutureCallback() {
                @Override
                public void completed(String content) {

                    Log.e("The xiaomi auth result is "+content);
                    try{

                        AuthInfo info = (AuthInfo)JsonUtils.decodeJson(content, AuthInfo.class);
                        if(info != null && info.getErrcode() == 200){
                            SDKVerifyResult vResult = new SDKVerifyResult(true, sid+"", "", "");
                            callback.onSuccess(vResult);
                            return;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the auth result is " + content);
                }

                @Override
                public void failed(String e) {
                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
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

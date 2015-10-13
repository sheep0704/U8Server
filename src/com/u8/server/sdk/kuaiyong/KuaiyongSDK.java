package com.u8.server.sdk.kuaiyong;

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
 * Created by ant on 2015/8/10.
 */
public class KuaiyongSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            Log.e("The extension is " + extension);

            JSONObject json = JSONObject.fromObject(extension);
            String token = json.getString("token");
            final String userName = json.getString("username");

            Map<String,String> params = new HashMap<String, String>();
            params.put("tokenKey", token);

            String sign = EncryptUtils.md5(channel.getCpAppID() + token);

            params.put("sign", sign);

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        Log.e("The auth result is " + result);

                        JSONObject json = JSONObject.fromObject(result);
                        int state = json.getJSONObject("state").getInt("code");

                        if (state == 1) {
                            SDKVerifyResult vResult = new SDKVerifyResult(true, "", userName, "");

                            callback.onSuccess(vResult);

                            return;
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the result is " + result);
                }

                @Override
                public void failed(String e) {
                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
                }

            });

        }catch(Exception e){
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

package com.u8.server.sdk.youlong;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 游龙SDK
 * Created by ant on 2015/8/18.
 */
public class YouLongSDK implements ISDKScript{


    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {
        try{


            JSONObject json = JSONObject.fromObject(extension);

            final String username = json.getString("username");
            final String token = json.getString("token");

            UHttpAgent httpClient = UHttpAgent.getInstance();

            Map<String, String> params = new HashMap<String, String>();
            params.put("token", token);
            params.put("pid", channel.getCpAppID());

            httpClient.post(channel.getMaster().getAuthUrl(), params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {
                        Log.d("The auth result is " + URLDecoder.decode(result, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    JSONObject json = JSONObject.fromObject(result);
                    if(json.containsKey("state") && json.getInt("state") == 1){

                        callback.onSuccess(new SDKVerifyResult(true, "", username, ""));
                        return;
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + result);

                }

                @Override
                public void failed(String e) {
                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
                }


            });




        }catch (Exception e){
            callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed. the exception is "+e.getMessage());
            Log.e(e.getMessage());
        }
    }

    @Override
    public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback) {
        if(callback != null){
            callback.onSuccess("");
        }
    }
}

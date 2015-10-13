package com.u8.server.sdk.wandoujia;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 豌豆荚SDK
 * Created by ant on 2015/4/25.
 */
public class WanDouJiaSDK implements ISDKScript{


    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);


            final String uid = json.getString("uid");
            final String token = json.getString("token");

            Map<String,String> params = new HashMap<String, String>();
            params.put("uid", uid);
            params.put("token", token);
            params.put("appkey", channel.getCpAppKey());

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        if("true".equals(result)){
                            SDKVerifyResult vResult = new SDKVerifyResult(true, uid, "", "");

                            callback.onSuccess(vResult);

                            return;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + result);
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

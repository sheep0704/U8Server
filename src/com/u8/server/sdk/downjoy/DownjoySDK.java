package com.u8.server.sdk.downjoy;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.EncryptUtils;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 当乐SDK
 * Created by ant on 2015/2/9.
 */
public class DownjoySDK implements ISDKScript {


    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try {

            JSONObject json = JSONObject.fromObject(extension);
            final String mid = json.getString("mid");
            final String token = json.getString("token");

            Map<String, String> data = assemblyParameters(channel, mid, token);

            UHttpAgent.getInstance().get(channel.getMaster().getAuthUrl(), data, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {
                    try {

                        DownjoyResponse res = (DownjoyResponse) JsonUtils.decodeJson(result, DownjoyResponse.class);

                        if (res != null) {
                            int code = res.getError_code();
                            if (code == 0) {
                                SDKVerifyResult vResult = new SDKVerifyResult(true, res.getMemberId() + "", res.getUsername(), res.getNickname());
                                callback.onSuccess(vResult);
                                return;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the get result is " + result);
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

    private Map<String, String> assemblyParameters(UChannel channel, String sid, String token){

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", channel.getCpAppID());
        data.put("umid", sid);
        data.put("token", token);
        data.put("sig", getSig(channel, token, sid));

        return data;
    }

    private String getSig(UChannel channel, String token, String sid){

        return EncryptUtils.md5(channel.getCpAppID()+"|"+channel.getCpAppKey()+"|"+token+"|"+sid);
    }



}

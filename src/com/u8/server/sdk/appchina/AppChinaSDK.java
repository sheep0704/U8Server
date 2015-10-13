package com.u8.server.sdk.appchina;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用汇
 * Created by ant on 2015/4/28.
 */
public class AppChinaSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {


        try{

            String ticket = extension;

            Map<String,String> params = new HashMap<String, String>();
            params.put("ticket", ticket);
            params.put("app_id", channel.getCpAppID());
            params.put("app_key", channel.getCpAppKey());

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        Log.e("The auth result is " + result);

                        AppChinaAuthInfo authInfo = (AppChinaAuthInfo) JsonUtils.decodeJson(result, AppChinaAuthInfo.class);

                        if(authInfo != null && authInfo.getStatus() == 0){

                            callback.onSuccess(new SDKVerifyResult(true, authInfo.getData().getUser_id(), authInfo.getData().getUser_name(), authInfo.getData().getNick_name()));
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

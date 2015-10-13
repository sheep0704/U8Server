package com.u8.server.sdk.m4399;

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
 * Created by ant on 2015/4/22.
 */
public class M4399SDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String uid = json.getString("uid");
            String state = json.getString("token");
            final String nickName = json.getString("nickname");
            final String userName = json.getString("username");


            Map<String,String> params = new HashMap<String, String>();
            params.put("uid", uid);
            params.put("state", state);
            params.put("key", channel.getCpAppKey());
            StringBuilder sb = new StringBuilder();
            sb.append(channel.getCpAppKey()).append(uid).append(state).append(channel.getCpAppSecret());
            String sign = EncryptUtils.md5(sb.toString()).toLowerCase();

            params.put("sign", sign);

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        Log.e("The auth result is "+result);

                        AuthInfo res = (AuthInfo) JsonUtils.decodeJson(result, AuthInfo.class);


                        if (res != null && res.getCode() == 100) {

                            SDKVerifyResult vResult = new SDKVerifyResult(true, uid, userName, nickName);

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

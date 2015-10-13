package com.u8.server.sdk.anzhi;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.Base64;
import com.u8.server.utils.TimeFormater;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 安智
 * Created by ant on 2015/4/29.
 */
public class AnzhiSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, final String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String uid = json.getString("uid");
            final String sid = json.getString("sid");

            Map<String,String> params = new HashMap<String, String>();
            params.put("time", TimeFormater.format_yyyyMMddHHmmssSSS(new Date()));
            params.put("appkey", channel.getCpAppKey());
            params.put("sid", sid);

            StringBuilder sb = new StringBuilder();
            sb.append(channel.getCpAppKey()).append(sid).append(channel.getCpAppSecret());
            String sign = Base64.encode(sb.toString(), "UTF-8");

            params.put("sign", sign);

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().post(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {
                        Log.e("The auth result is " + result);

                        JSONObject json = JSONObject.fromObject(result);
                        String sc = json.getString("sc");

                        if("1".equals(sc)){

                            callback.onSuccess(new SDKVerifyResult(true, uid, "", ""));
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

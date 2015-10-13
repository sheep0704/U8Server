package com.u8.server.sdk.ouwan;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.sdk.ISDKOrderListener;
import com.u8.server.sdk.ISDKScript;
import com.u8.server.sdk.ISDKVerifyListener;
import com.u8.server.sdk.SDKVerifyResult;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;

/**
 * 偶玩
 * Created by ant on 2015/5/4.
 */
public class OuWanSDK implements ISDKScript{
    @Override
    public void verify(UChannel channel, String extension, ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String uid = json.getString("uid");
            String sign = json.getString("sign");
            String st = json.getString("st");

            if(Math.abs(System.currentTimeMillis()/1000 - Integer.valueOf(st)) > 600){
                callback.onFailed("The token is out of date");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(uid).append("&").append(st).append("&").append(channel.getCpAppSecret());
            String vCode = EncryptUtils.md5(sb.toString()).toLowerCase();

            if(vCode.equals(sign)){
                callback.onSuccess(new SDKVerifyResult(true, uid, "", ""));
            }else{
                callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed.uid"+uid+";ext:"+extension);
            }


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

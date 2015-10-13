package com.u8.server.sdk.vivo;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.EncryptUtils;
import com.u8.server.utils.JsonUtils;
import com.u8.server.utils.TimeFormater;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ant on 2015/4/23.
 */
public class VivoSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{



            JSONObject json = JSONObject.fromObject(extension);
            final String openid = json.getString("openid");
            final String name = json.getString("name");
            final String token = json.getString("token");

            Map<String, String> params = new HashMap<String, String>();
            params.put("access_token", token);

            UHttpAgent.getInstance().post(channel.getMaster().getAuthUrl(), params, new UHttpFutureCallback() {
                @Override
                public void completed(String content) {

                    Log.e("The auth result is "+content);
                    try{

                        AuthInfo authInfo = (AuthInfo)JsonUtils.decodeJson(content, AuthInfo.class);
                        if(authInfo != null && authInfo.getUid().equals(openid)){
                            callback.onSuccess(new SDKVerifyResult(true, openid, name, ""));
                            return;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + content);

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

            try{

                UChannel channel = order.getChannel();
                if(channel == null){
                    Log.e("The channel is not exists of order "+order.getOrderID());
                    return;
                }

                String orderUrl = channel.getMaster().getOrderUrl();

                String version = "1.0.0";
                String signMethod = "MD5";
                String signature = "";
                String cpId = order.getChannel().getCpID();
                String appId = order.getChannel().getCpAppID();
                String cpOrderNumber = ""+order.getOrderID();
                String notifyUrl = channel.getMaster().getPayCallbackUrl();
                String orderTime = TimeFormater.format_yyyyMMddHHmmss(order.getCreatedTime());
                String orderAmount = order.getMoney() + "";
                String orderTitle = order.getProductName();
                String orderDesc = order.getProductDesc();
                String extInfo = "";        //空字符串不参与签名

                StringBuilder sb = new StringBuilder();
                sb.append("appId=").append(appId).append("&")
                .append("cpId=").append(cpId).append("&")
                .append("cpOrderNumber=").append(cpOrderNumber).append("&")
//                .append("extInfo=").append(extInfo).append("&")
                .append("notifyUrl=").append(notifyUrl).append("&")
                .append("orderAmount=").append(orderAmount).append("&")
                .append("orderDesc=").append(orderDesc).append("&")
                .append("orderTime=").append(orderTime).append("&")
                .append("orderTitle=").append(orderTitle).append("&")
                .append("version=").append(version).append("&")
                .append(EncryptUtils.md5(channel.getCpAppKey()).toLowerCase());

                signature = EncryptUtils.md5(sb.toString()).toLowerCase();

                Map<String,String> params = new HashMap<String, String>();
                params.put("version", version);
                params.put("signMethod", signMethod);
                params.put("signature", signature);
                params.put("cpId", cpId);
                params.put("appId", appId);
                params.put("cpOrderNumber", cpOrderNumber);
                params.put("notifyUrl", notifyUrl);
                params.put("orderTime", orderTime);
                params.put("orderAmount", orderAmount);
                params.put("orderTitle", orderTitle);
                params.put("orderDesc", orderTitle);
                params.put("extInfo", extInfo);

                String result = UHttpAgent.getInstance().post(orderUrl, params);

                Log.e("The vivo order result is "+result);

                VivoOrderResult orderResult = (VivoOrderResult)JsonUtils.decodeJson(result, VivoOrderResult.class);
                if(orderResult != null && orderResult.getRespCode() == 200){

                    JSONObject ext = new JSONObject();
                    ext.put("transNo", orderResult.getOrderNumber());
                    ext.put("accessKey", orderResult.getAccessKey());
                    String extStr = ext.toString();
                    callback.onSuccess(extStr);

                }else{

                    callback.onFailed("the vivo order result is "+result);

                }

            }catch (Exception e){
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            }



        }
    }
}

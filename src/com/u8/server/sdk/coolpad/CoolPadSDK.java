package com.u8.server.sdk.coolpad;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CoolPadSDK暂时只支持消费型_应用传入价格类型的网游应用
 * waresid默认就一个，配置在UChannel的cpConfig字段中
 * Created by ant on 2015/4/8.
 */
public class CoolPadSDK implements ISDKScript{

    private static final String USER_INFO_URL = "https://openapi.coolyun.com/oauth2/api/get_user_info";


    private String getUserInfo(UChannel channel, CoolPadTokenInfo tokenInfo){

        Map<String, String> data = new HashMap<String, String>();
        data.put("access_token", tokenInfo.getAccess_token());
        data.put("oauth_consumer_key", channel.getCpAppID());
        data.put("openid", tokenInfo.getOpenid());

        return UHttpAgent.getInstance().get(USER_INFO_URL, data);

    }

    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            String code = extension;
            Map<String, String> params = new HashMap<String, String>();
            params.put("grant_type", "authorization_code");
            params.put("client_id", channel.getCpAppID());
            params.put("client_secret", channel.getCpAppKey());
            params.put("code", code);
            params.put("redirect_uri", channel.getCpAppKey());

            UHttpAgent.getInstance().get(channel.getMaster().getAuthUrl(), params, new UHttpFutureCallback() {

                @Override
                public void completed(String content) {

                    try{

                        CoolPadTokenInfo token = (CoolPadTokenInfo)JsonUtils.decodeJson(content, CoolPadTokenInfo.class);

                        String userInfoString = getUserInfo(channel, token);

                        Log.d("The userInfo String is "+userInfoString);

                        CoolPadUserInfo user = (CoolPadUserInfo)JsonUtils.decodeJson(userInfoString, CoolPadUserInfo.class);

                        if(user != null && "0".equals(user.getRtn_code())){

                            JSONObject json = new JSONObject();
                            json.put("access_token", token.getAccess_token());
                            json.put("openid", token.getOpenid());
                            String ext = json.toString();

                            SDKVerifyResult verifyResult = new SDKVerifyResult(true, token.getOpenid(), user.getNickname(), user.getNickname(), ext);
                            callback.onSuccess(verifyResult);
                            return;
                        }

                        callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + content);



                    }catch (Exception e){
                        callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the post result is " + content+";"+e.getMessage());
                        e.printStackTrace();
                    }

                }

                @Override
                public void failed(String e) {

                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);                }

            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onGetOrderID(UUser user, final UOrder order, final ISDKOrderListener callback) {

        JSONObject data = new JSONObject();
        data.put("appid", user.getChannel().getCpAppID());
        data.put("waresid", Integer.valueOf(user.getChannel().getCpConfig()));
        data.put("waresname", order.getProductName());
        data.put("cporderid", ""+order.getOrderID());
        float price = order.getMoney() / 100f;
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(price);//format 返回的是字符串

        data.put("price", Float.valueOf(p));
        data.put("currency", "RMB");
        data.put("appuserid", order.getRoleID()+"#"+order.getServerID());
        //data.put("cpprivateinfo", ""+order.getOrderID());
        //data.put("notifyurl", order.getChannel().getMaster().getPayCallbackUrl());
        final String transdata = data.toString();

        String sign = SignHelper.sign(transdata, order.getChannel().getCpPayPriKey());

        String signType = "RSA";

        Map<String, String> params = new HashMap<String, String>();
        params.put("transdata", transdata);
        params.put("sign", sign);
        params.put("signtype", signType);

        UHttpAgent.getInstance().post(order.getChannel().getMaster().getOrderUrl(), params, new UHttpFutureCallback() {
            @Override
            public void completed(String content) {

                String transid = "";
                try {
                    content = URLDecoder.decode(content, "UTF-8");

                    String[] data = content.split("&");
                    for(String dataItem : data){
                        if(dataItem.startsWith("transdata=")){
                            String[] transdata = dataItem.split("=");
                            JSONObject json = JSONObject.fromObject(transdata[1]);
                            transid = json.getString("transid");
                        }
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                Log.d(order.getChannel().getChannelID() + " get channel order completed. content is "+content);
                callback.onSuccess(transid);
            }

            @Override
            public void failed(String e) {
                callback.onFailed(order.getChannel().getChannelID() + " get channel order failed. " + e);
            }

        });

    }
}

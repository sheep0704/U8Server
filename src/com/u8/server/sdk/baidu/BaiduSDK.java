package com.u8.server.sdk.baidu;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.sdk.*;
import com.u8.server.utils.Base64;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * Created by ant on 2015/2/28.
 */
public class BaiduSDK implements ISDKScript {

    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            String token = extension;
            ByteArrayEntity params = encodeParams(channel, token);
            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().post(url, null, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try{

                        BaiduResponse res = parseBaiduResponse(result);


                        if(res != null && res.getResultCode() == 1 && isValid(channel, res)){
                            String content = URLDecoder.decode(res.getContent(), "utf-8");

                            String jsonStr = Base64.decode(content);

                            BaiduContent contObj = parseBaiduContent(jsonStr);

                            if(contObj != null){
                                SDKVerifyResult vResult = new SDKVerifyResult(true, contObj.getUID()+"", "", "");

                                callback.onSuccess(vResult);
                                return;
                            }


                        }

                    }catch(Exception e){
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

    BaiduContent parseBaiduContent(String content){
        JSONObject json = JSONObject.fromObject(content);
        BaiduContent c = new BaiduContent();
        c.setUID(json.getLong("UID"));

        return c;
    }

    BaiduResponse parseBaiduResponse(String result){
        JSONObject json = JSONObject.fromObject(result);
        BaiduResponse res = new BaiduResponse();
        res.setAppID(json.getInt("AppID"));
        res.setResultCode(json.getInt("ResultCode"));
        res.setSign(json.getString("Sign"));
        res.setContent(json.getString("Content"));
        res.setResultMsg(json.getString("ResultMsg"));
        return res;
    }

    @Override
    public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback){
        if(callback != null){
            callback.onSuccess("");
        }
    }

    private boolean isValid(UChannel channel, BaiduResponse res) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(channel.getCpAppID()).append(res.getResultCode())
                .append(URLDecoder.decode(res.getContent(), "utf-8"))
                .append(channel.getCpAppSecret());

        String sign1 = EncryptUtils.md5(sb.toString()).toLowerCase();

        return sign1.equals(res.getSign().toLowerCase());
    }

    private ByteArrayEntity encodeParams(UChannel channel, String accessToken){

        String sign = EncryptUtils.md5(channel.getCpAppID()+accessToken+channel.getCpAppSecret());

        StringBuilder param = new StringBuilder();
        param.append("AppID=");
        param.append(channel.getCpAppID());
        param.append("&AccessToken=");
        param.append(accessToken);
        param.append("&Sign=");
        param.append(sign.toLowerCase());

        String s = param.toString();

        return new ByteArrayEntity(s.getBytes(Charset.forName("UTF-8")));

    }
}

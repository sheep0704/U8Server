package com.u8.server.sdk.uc;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;
import javassist.bytecode.ByteArray;
import org.apache.http.entity.ByteArrayEntity;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by ant on 2014/12/12.
 */
public class UCSDK implements ISDKScript {

    @Override
    public void verify(final UChannel channel,  String extension, final ISDKVerifyListener callback) {

        try{

            UHttpAgent httpClient = UHttpAgent.getInstance();


            Map<String, Object> data = new HashMap<String, Object>();
            data.put("sid", extension);
            String url = channel.getMaster().getAuthUrl();
            Map<String, Object> proData = assemblyParameters(channel, ConfigHelper.VERIFYSESSION, data);

            String jsonData = JsonUtils.encodeJson(proData);

            Map<String,String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            httpClient.post(url, headers, new ByteArrayEntity(jsonData.getBytes(Charset.forName("UTF-8"))), new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    VerifySessionResponse rsp = (VerifySessionResponse) JsonUtils.decodeJson(result, VerifySessionResponse.class);

                    if (rsp != null) {
                        int code = rsp.getState().getCode();
                        if (code == 1) {
                            SDKVerifyResult verifyResult = new SDKVerifyResult(true, rsp.getData().getAccountId(), rsp.getData().getAccountId(), rsp.getData().getNickName());
                            callback.onSuccess(verifyResult);
                            return;
                        }
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

    private Map<String, Object> assemblyParameters(UChannel channel, String service, Map<String,Object> data) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", System.currentTimeMillis());// 当前系统时间
        params.put("service", service);

        Map<String,Object> game = new HashMap<String, Object>();
        game.put("gameId", channel.getCpAppID());

        params.put("game", game);
        params.put("data", data);
        params.put("encrypt", "md5");
		/*
		 * 签名规则=签名内容+apiKey 假定apiKey=202cb962234w4ers2aaa,sid=abcdefg123456 那么签名原文sid=abcdefg123456202cb962234w4ers2aaa
		 * 签名结果6e9c3c1e7d99293dfc0c81442f9a9984
		 */
        String signSource = getSignData(data) + channel.getCpAppKey();
        // String signSource = "sid=70b37f00-5f59-4819-99ad-8bde027ade00144882"+apiKey;
        String sign = getMD5Str(signSource);// MD5加密签名
        Log.d("[签名原文]" + signSource);
        Log.d("[签名结果]" + sign);
        params.put("sign", sign);
        String body = JsonUtils.encodeJson(params);// 把参数序列化成一个json字符串
        Log.d("[请求参数]" + body);
        return params;
    }

    private static String getSignData(Map params) {
        StringBuffer content = new StringBuffer();

        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key) == null ? "" : params.get(key).toString();
            if (value != null) {
                content.append( key + "=" + value);
            } else {
                content.append(key + "=");
            }
        }

        return content.toString();
    }

    private static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            Log.e("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            Log.e(e.toString());
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /***
     * 验证支付
     * @param channel
     * @param rsp
     * @return
     */
    public boolean verifyPay(UChannel channel, PayCallbackResponse rsp){

        String signSource= "accountId="+rsp.getData().getAccountId()+"amount="+rsp.getData().getAmount()+"callbackInfo="+rsp.getData().getCallbackInfo()
                +"cpOrderId="+rsp.getData().getCpOrderId()+"creator="+rsp.getData().getCreator()+"failedDesc="+rsp.getData().getFailedDesc()+"gameId="+rsp.getData().getGameId()
                +"orderId="+rsp.getData().getOrderId()+"orderStatus="+rsp.getData().getOrderStatus()
                +"payWay="+rsp.getData().getPayWay()+"serverId="+rsp.getData().getServerId()
                +channel.getCpAppKey();

        String sign = getMD5Str(signSource);

        return sign.equals(rsp.getSign());

    }

    /***
     * 验证支付
     * @param channel
     * @param rsp
     * @return
     */
    public String generatePaySign(UChannel channel, PayCallbackResponse rsp){

        String signSource= "accountId="+rsp.getData().getAccountId()
                +"amount="+rsp.getData().getAmount()
                +"callbackInfo="+rsp.getData().getCallbackInfo()
                +"cpOrderId="+rsp.getData().getCpOrderId()+"creator="+rsp.getData().getCreator()+"failedDesc="+rsp.getData().getFailedDesc()+"gameId="+rsp.getData().getGameId()
                +"orderId="+rsp.getData().getOrderId()+"orderStatus="+rsp.getData().getOrderStatus()
                +"payWay="+rsp.getData().getPayWay()+"serverId="+rsp.getData().getServerId()
                +channel.getCpAppKey();

        String sign = getMD5Str(signSource);

        return sign;

    }
}

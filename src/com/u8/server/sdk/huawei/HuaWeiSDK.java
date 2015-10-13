package com.u8.server.sdk.huawei;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;
import com.u8.server.utils.RSAUtils;

import java.util.*;

/**
 * Created by ant on 2015/4/27.
 */
public class HuaWeiSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel,String extension, final ISDKVerifyListener callback) {

        try{

            final String token = extension;

            Map<String,String> params = new HashMap<String, String>();
            params.put("nsp_svc", "OpenUP.User.getInfo");
            params.put("nsp_ts", ""+(System.currentTimeMillis() / 1000));
            params.put("access_token", token);

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        Log.e("The auth result is " + result);

                        if (result != null && !result.contains("error")) {
                            HuaWeiAuthInfo res = (HuaWeiAuthInfo) JsonUtils.decodeJson(result, HuaWeiAuthInfo.class);

                            if (res != null) {

                                SDKVerifyResult vResult = new SDKVerifyResult(true, res.getUserID(), res.getUserName(), "");

                                callback.onSuccess(vResult);

                                return;
                            }

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
            String sign = getSign(user.getChannel(), order);
            callback.onSuccess(sign);
        }
    }

    private String getSign(UChannel channel, UOrder data){

        int money = (int)(data.getMoney() / 100);
        Map<String, String> params = new HashMap<String, String>();
        // 必填字段，这里支付ID配置在
        params.put("userID", data.getChannel().getCpPayID());
        // 必填字段，不能为null或者""，请填写从联盟获取的应用ID
        params.put("applicationID", data.getChannel().getCpAppID());
        // 必填字段，不能为null或者""，单位是元，精确到小数点后两位，如1.00
        params.put("amount", money+".00");
        // 必填字段，不能为null或者""，道具名称
        params.put("productName", data.getProductName());
        // 必填字段，不能为null或者""，道具描述
        params.put("productDesc", data.getProductDesc());
        // 必填字段，不能为null或者""，最长30字节，不能重复，否则订单会失败
        params.put("requestId", data.getOrderID()+"");

        String noSign = getSignData(params);

        Log.d("The noSign data is "+noSign);

        String sign = RSAUtils.sign(noSign, channel.getCpPayPriKey(), "UTF-8");
        Log.d("The sign is ： " + sign);

        return sign;
    }

    public static String getSignData(Map<String, String> params)
    {
        StringBuffer content = new StringBuffer();
        // 按照key做排序
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++)
        {
            String key = (String) keys.get(i);
            if ("sign".equals(key))  //  如果是sign字段，字不作为签名校验参数
            {
                continue;
            }
            String value = (String) params.get(key);
            if (value != null)
            {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }
            else   //  如果value为null，则写成：key=&key1=value1
            {
                content.append((i == 0 ? "" : "&") + key + "=");
            }
        }
        return content.toString();
    }
}

package com.u8.server.sdk.qihoo360;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ant on 2015/2/27.
 */
public class Qihoo360SDK implements ISDKScript {

    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            Map<String, String> data = new HashMap<String, String>();
            data.put("access_token", extension);
            data.put("fields", "id,name,avatar,sex,area,nick");
            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, data, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try{

                        QihooUserInfo userInfo = (QihooUserInfo) JsonUtils.decodeJson(result, QihooUserInfo.class);

                        if(userInfo != null && !StringUtils.isEmpty(userInfo.getId())){
                            SDKVerifyResult vResult = new SDKVerifyResult(true, userInfo.getId(), userInfo.getName(), userInfo.getNick());
                            callback.onSuccess(vResult);
                            return;
                        }

                    }catch (Exception e){
                        e.printStackTrace();

                        QihooErrorInfo error = (QihooErrorInfo)JsonUtils.decodeJson(result, QihooErrorInfo.class);
                        if(error != null){
                            callback.onFailed(channel.getMaster().getSdkName()+" verify failed. msg:"+error.getError());
                            return;
                        }

                    }



                    callback.onFailed(channel.getMaster().getSdkName() +" verify failed. the get result is "+result);
                }

                @Override
                public void failed(String e) {
                    callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
                }


            });


        }catch(Exception e){
            callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed. the exception is "+e.getMessage());
            e.printStackTrace();
        }



    }

    @Override
    public void onGetOrderID(UUser user, UOrder order, ISDKOrderListener callback) {

        JSONObject json = new JSONObject();

        try{

            String notifyUrl = order.getChannel().getMaster().getPayCallbackUrl();
            json.put("notifyUrl", notifyUrl);

        }catch (Exception e){
            e.printStackTrace();
        }

        if(callback != null){
            callback.onSuccess(json.toString());
        }
    }
}

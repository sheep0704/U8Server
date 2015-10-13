package com.u8.server.sdk.lenovo;

import com.thoughtworks.xstream.XStream;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 联想SDK
 * Created by ant on 2015/4/25.
 */
public class LenovoSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            final String lpsust = extension;

            Map<String,String> params = new HashMap<String, String>();
            params.put("lpsust", lpsust);
            params.put("realm", channel.getCpAppID());

            String url = channel.getMaster().getAuthUrl();

            UHttpAgent.getInstance().get(url, params, new UHttpFutureCallback() {
                @Override
                public void completed(String result) {

                    try {

                        if(result != null && result.contains("IdentityInfo")){
                            XStream xStream = new XStream();
                            xStream.alias("IdentityInfo", LenovoAuthInfo.class);
                            xStream.aliasField("AccountID", LenovoAuthInfo.class, "accountID");
                            xStream.aliasField("Username", LenovoAuthInfo.class, "username");
                            xStream.aliasField("DeviceID", LenovoAuthInfo.class, "deviceID");

                            LenovoAuthInfo authInfo = (LenovoAuthInfo)xStream.fromXML(result);

                            if(authInfo != null){
                                Log.e("The auth info is "+authInfo.getAccountID());
                                callback.onSuccess(new SDKVerifyResult(true, authInfo.getAccountID(), authInfo.getUsername(), ""));
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
            callback.onSuccess("");
        }

    }
}

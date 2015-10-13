package com.u8.server.sdk.pp;

import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.EncryptUtils;

import net.sf.json.JSONObject;
import org.apache.http.entity.ByteArrayEntity;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ant on 2015/8/10.
 */
public class PPSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject json = JSONObject.fromObject(extension);
            final String userName = json.getString("username");
            String sid = json.getString("sid");

            JSONObject params = new JSONObject();
            params.put("id", System.currentTimeMillis() / 1000);
            params.put("service", "account.verifySession");

            JSONObject game = new JSONObject();
            game.put("gameId", channel.getCpAppID());

            params.put("game", game);
            params.put("encrypt", "MD5");
            params.put("sign", EncryptUtils.md5("sid=" + channel.getCpAppID() + channel.getCpAppKey()));

            params.put("sid", sid);

            String url = channel.getMaster().getAuthUrl();

            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Content-Type", "application/json");

            UHttpAgent.getInstance().post(url, headers, new ByteArrayEntity(params.toString().getBytes(Charset.forName("UTF-8"))), new UHttpFutureCallback() {
                        @Override
                        public void completed(String result) {

                            try {

                                JSONObject json = JSONObject.fromObject(result);
                                int state = json.getJSONObject("state").getInt("code");

                                if (state == 1) {
                                    SDKVerifyResult vResult = new SDKVerifyResult(true, "", userName, "");

                                    callback.onSuccess(vResult);

                                    return;
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            callback.onFailed(channel.getMaster().getSdkName() + " verify failed. the result is " + result);
                        }

                        @Override
                        public void failed(String e) {
                            callback.onFailed(channel.getMaster().getSdkName() + " verify failed. " + e);
                        }

                    });

        }catch(Exception e){
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

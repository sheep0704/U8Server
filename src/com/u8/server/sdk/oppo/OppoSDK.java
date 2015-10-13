package com.u8.server.sdk.oppo;

import com.nearme.oauth.model.AccessToken;
import com.nearme.oauth.open.AccountAgent;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.*;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;

/**
 * Created by ant on 2015/4/22.
 */
public class OppoSDK implements ISDKScript{
    @Override
    public void verify(final UChannel channel, String extension, final ISDKVerifyListener callback) {

        try{

            JSONObject params = JSONObject.fromObject(extension);
            String token = params.getString("token");
            String tokenSecret = params.getString("secret");

            String gcUserInfo = AccountAgent.getInstance().getGCUserInfo(new AccessToken(token, tokenSecret));

            JSONObject json = JSONObject.fromObject(gcUserInfo);
            String briefUser = json.getString("BriefUser");
            OppoBriefUser user = (OppoBriefUser) JsonUtils.decodeJson(briefUser, OppoBriefUser.class);

            if(user != null){
                callback.onSuccess(new SDKVerifyResult(true, user.getId(), user.getUserName(), user.getName()));
                return;
            }

            callback.onFailed(channel.getMaster().getSdkName() + " verify execute failed.");

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

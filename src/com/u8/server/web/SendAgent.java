package com.u8.server.web;

import com.u8.server.constants.PayState;
import com.u8.server.constants.StateCode;
import com.u8.server.data.UGame;
import com.u8.server.data.UOrder;
import com.u8.server.sdk.UHttpAgent;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.UGenerator;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ByteArrayEntity;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ant on 2015/2/9.
 */
public class SendAgent {

    public static boolean sendCallbackToServer(UOrderManager orderManager, UOrder order){

        UGame game = order.getGame();
        if(game == null){
            return false;
        }

        if(StringUtils.isEmpty(game.getPayCallback())){

            return false;
        }

        JSONObject data = new JSONObject();
        data.put("orderID", order.getOrderID());
        data.put("userID", order.getUserID());
        data.put("gameID", order.getAppID());
        data.put("money", order.getMoney());
        data.put("currency", order.getCurrency());
        data.put("extension", order.getExtension());

        JSONObject response = new JSONObject();
        response.put("state", StateCode.CODE_AUTH_SUCCESS);
        response.put("data", data);
        response.put("sign", UGenerator.generateSign(order));

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/html");

        String serverRes = UHttpAgent.getInstance().post(game.getPayCallback(), headers, new ByteArrayEntity(response.toString().getBytes(Charset.forName("UTF-8"))));

        if(serverRes.equals("SUCCESS")){
            order.setState(PayState.STATE_COMPLETE);
            orderManager.saveOrder(order);
            return true;
        }

        return false;
    }

}

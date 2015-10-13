package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.coolpad.CoolPadPayResult;
import com.u8.server.sdk.coolpad.CoolPadSDK;
import com.u8.server.sdk.coolpad.SignHelper;
import com.u8.server.service.UChannelManager;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.EncryptUtils;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 酷派支付回调
 * Created by ant on 2015/9/15.
 */
@Controller
@Namespace("/pay/coolpad")
public class CoolPadPayCallbackAction extends UActionSupport{


    private String transdata;
    private String sign;
    private String signtype;

    private int u8ChannelID;

    @Autowired
    private UOrderManager orderManager;

    @Autowired
    private UChannelManager channelManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            UChannel channel = channelManager.queryChannel(this.u8ChannelID);
            if(channel == null){
                Log.e("The channel is not exists. channelID:"+this.u8ChannelID+";data:"+this.transdata);
                return;
            }

            if(!isSignOK(channel)){
                Log.d("The sign verify failed.sign:%s;appKey:%s;transdata:%s", sign, channel.getCpPayKey(), transdata);
                this.renderState(false);
                return;
            }

            CoolPadPayResult result = (CoolPadPayResult) JsonUtils.decodeJson(this.transdata, CoolPadPayResult.class);

            if(result == null){
                Log.e("The data parse error...");
                this.renderState(false);
                return;
            }


            long orderID = Long.parseLong(result.getCporderid());

            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false);
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE) {
                Log.d("The state of the order is complete. The state is " + order.getState());
                this.renderState(true);
                return;
            }

            if("0".equals(result.getResult())){

                float money = Float.parseFloat(result.getMoney());
                int moneyInt = (int)(money * 100);  //以分为单位

                order.setMoney(moneyInt);
                order.setChannelOrderID(result.getTransid());
                order.setState(PayState.STATE_SUC);

                orderManager.saveOrder(order);

                SendAgent.sendCallbackToServer(this.orderManager, order);

            }else{
                order.setChannelOrderID(result.getTransid());
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
            }


        }catch(Exception e){
            try {
                renderState(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private boolean isSignOK(UChannel channel){

        return SignHelper.verify(transdata, this.sign, channel.getCpPayKey());
    }

    private void renderState(boolean suc) throws IOException {

        String r = "SUCCESS";
        if(!suc){
            r = "FAILURE";
        }

        this.response.getWriter().write(r);
    }

    public String getTransdata() {
        return transdata;
    }

    public void setTransdata(String transdata) {
        this.transdata = transdata;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSigntype() {
        return signtype;
    }

    public void setSigntype(String signtype) {
        this.signtype = signtype;
    }

    public int getU8ChannelID() {
        return u8ChannelID;
    }

    public void setU8ChannelID(int u8ChannelID) {
        this.u8ChannelID = u8ChannelID;
    }
}

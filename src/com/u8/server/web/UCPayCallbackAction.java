package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.constants.StateCode;
import com.u8.server.data.UGame;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.UHttpAgent;
import com.u8.server.sdk.uc.PayCallbackResponse;
import com.u8.server.sdk.uc.UCSDK;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.JsonUtils;
import com.u8.server.utils.UGenerator;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;


/***
 * UC渠道的支付回调请求处理
 */
@Controller
@Namespace("/pay/uc")
public class UCPayCallbackAction extends UActionSupport{

    @Autowired
    private UOrderManager orderManager;

    private int u8ChannelID;

    @Action("payCallback")
    public void payCallback(){

        try{

            BufferedReader br = this.request.getReader();
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine()) != null){
                sb.append(line).append("\r\n");
            }

            Log.d("UC Pay Callback . request params:" + sb.toString());

            PayCallbackResponse rsp = (PayCallbackResponse) JsonUtils.decodeJson(sb.toString(), PayCallbackResponse.class);

            if(rsp == null){
                this.renderState(false);
                return;
            }

            long orderID = Long.parseLong(rsp.getData().getCallbackInfo());
            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false);
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(true);
                return;
            }


            UCSDK sdk = new UCSDK();

            if(!sdk.verifyPay(order.getChannel(), rsp)){
                Log.d("The sign is not matched.");
                this.renderState(true);
                return;
            }

            if("S".equals(rsp.getData().getOrderStatus())){
                float money = Float.parseFloat(rsp.getData().getAmount());
                int moneyInt = (int)(money * 100);  //以分为单位

                order.setMoney(moneyInt);
                order.setChannelOrderID(rsp.getData().getOrderId());
                order.setState(PayState.STATE_SUC);

                orderManager.saveOrder(order);

                SendAgent.sendCallbackToServer(this.orderManager, order);


            }else{
                order.setChannelOrderID(rsp.getData().getOrderId());
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
            }


            renderState(true);

        }catch (Exception e){
            try{
                this.renderState(false);
            }catch (Exception e2){
                Log.e(e2.getMessage());
            }

            Log.e(e.getMessage());

        }

    }

    private void renderState(boolean suc) throws IOException{

        String res = "SUCCESS";
        if(!suc){
            res = "FAILURE";
        }

        this.response.getWriter().write(res);
    }

    public int getU8ChannelID() {
        return u8ChannelID;
    }

    public void setU8ChannelID(int u8ChannelID) {
        this.u8ChannelID = u8ChannelID;
    }
}

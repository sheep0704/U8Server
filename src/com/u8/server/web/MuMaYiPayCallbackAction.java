package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.mumayi.MMYPayResult;
import com.u8.server.sdk.mumayi.MuMaYiSDK;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.JsonUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 木蚂蚁支付回调
 * Created by ant on 2015/9/14.
 */
@Controller
@Namespace("/pay/mumayi")
public class MuMaYiPayCallbackAction extends UActionSupport{

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            BufferedReader br = this.request.getReader();
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine()) != null){
                sb.append(line).append("\r\n");
            }

            Log.d("MuMaYi Pay Callback . request params:" + sb.toString());

            MMYPayResult result = (MMYPayResult) JsonUtils.decodeJson(sb.toString(), MMYPayResult.class);

            if(result == null){
                renderState(false);
                return;
            }

            long orderID = Long.parseLong(result.getOrderID());

            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false);
                return;
            }

            UChannel channel = order.getChannel();
            if(channel == null){

                this.renderState(false);
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(true);
                return;
            }

            if(!MuMaYiSDK.verify(result.getTradeSign(), channel.getCpAppKey(), result.getOrderID())){
                Log.d("The sign verify failed.sign:%s;appKey:%s;orderID:%s", result.getTradeSign(), channel.getCpAppKey(), result.getOrderID());
                this.renderState(true);
                return;
            }

            //TODO:这里价格单位需要和木蚂蚁技术沟通确认下
            float money = Float.parseFloat(result.getProductPrice());
            int moneyInt = (int)(money * 100);  //以分为单位

            order.setMoney(moneyInt);
            order.setChannelOrderID("");        //渠道orderID貌似没有值
            order.setState(PayState.STATE_SUC);

            orderManager.saveOrder(order);

            SendAgent.sendCallbackToServer(this.orderManager, order);


        }catch(Exception e){
            try {
                renderState(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void renderState(boolean suc) throws IOException {

        String res = "success";
        if(!suc){
            res = "fail";
        }

        this.response.getWriter().write(res);
    }

}

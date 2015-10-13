package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.baidu.BaiduPayResult;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.Base64;
import com.u8.server.utils.EncryptUtils;
import com.u8.server.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 百度SDK充值回调接口
 * Created by ant on 2015/2/28.
 */

@Controller
@Namespace("/pay/baidu")
public class BaiduPayCallbackAction extends UActionSupport{

    private int AppID;
    private String OrderSerial;
    private String CooperatorOrderSerial;
    private String Sign;
    private String Content;

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){

        try{

            long orderID = Long.parseLong(CooperatorOrderSerial);

            UOrder order = orderManager.getOrder(orderID);

            int resultCode = 1;
            String resultMsg = "成功";

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(order.getChannel(), resultCode, resultMsg);
                return;
            }



            if (AppID == 0 || OrderSerial == null || CooperatorOrderSerial == null || Content == null || Sign == null){
                resultCode = 4;
                resultMsg="参数错误";
            }else{
                //appID验证
                if(!order.getChannel().getCpAppID().equals(AppID + "")){
                    resultCode= 2; //appid无效
                    resultMsg="AppID无效";
                }

                StringBuilder strSign = new StringBuilder();
                strSign.append(AppID);
                strSign.append(OrderSerial);
                strSign.append(CooperatorOrderSerial);
                strSign.append(Content);
                strSign.append(order.getChannel().getCpAppSecret());

                Log.e("The new sign is "+strSign.toString());
                //签名验证
                if(!EncryptUtils.md5(strSign.toString()).toLowerCase().equals(Sign.toLowerCase())){
                    resultCode= 3; //sign无效
                    resultMsg="Sign无效";
                }
            }

            if(resultCode == 1){
                String jsonStr= Base64.decode(Content);

                BaiduPayResult payResult = (BaiduPayResult) JsonUtils.decodeJson(jsonStr, BaiduPayResult.class);

                if(payResult != null && payResult.getOrderStatus() == 1){
                    order.setMoney((int)(payResult.getOrderMoney() * 100));
                    order.setChannelOrderID(OrderSerial);
                    order.setState(PayState.STATE_SUC);
                    orderManager.saveOrder(order);
                    SendAgent.sendCallbackToServer(this.orderManager, order);
                }else{
                    Log.e("The pay result decode2Bytes error. The content is "+jsonStr);
                }


            }else{
                order.setChannelOrderID(OrderSerial);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
            }

            renderState(order.getChannel(), resultCode, resultMsg);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void renderState(UChannel channel, int resultCode, String resultMsg) throws IOException {

        JSONObject json = new JSONObject();
        json.put("AppID", channel.getCpAppID());
        json.put("ResultCode", resultCode);
        json.put("ResultMsg", resultMsg);
        json.put("Sign", EncryptUtils.md5(channel.getCpAppID()+resultCode+channel.getCpAppSecret()));
        json.put("Content", "");

        Log.d("The result to sdk is "+json.toString());

        this.response.getWriter().write(json.toString());


    }

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        AppID = appID;
    }

    public String getOrderSerial() {
        return OrderSerial;
    }

    public void setOrderSerial(String orderSerial) {
        OrderSerial = orderSerial;
    }

    public String getCooperatorOrderSerial() {
        return CooperatorOrderSerial;
    }

    public void setCooperatorOrderSerial(String cooperatorOrderSerial) {
        CooperatorOrderSerial = cooperatorOrderSerial;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}

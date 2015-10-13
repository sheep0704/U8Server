package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.sdk.downjoy.DownjoyMD5;
import com.u8.server.sdk.downjoy.DownjoySDK;
import com.u8.server.service.UOrderManager;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 *  当乐渠道的支付回调请求处理
 * Created by ant on 2015/2/9.
 */
@Controller
@Namespace("/pay/downjoy")
public class DownjoyPayCallbackAction extends UActionSupport{

    private int result; //支付结果，固定值。“1”代表成功，“0”代表失败
    private String money;  //支付金额，单位：元。//这里money使用String类型，不要使用float类型，否则导致精度丢失，生成md5可能出错
    private String order;//本次支付的订单号。
    private String mid;   //本次支付用户的乐号，既登录后返回的 mid 参数。
    private String time;//时间戳，格式：yyyymmddHH24mmss 月日小时分秒小于 10 前面补充 0
    private String signature;//MD5 验证串，用于与接口生成的验证串做比较，保证计费通知的合法性。
    private String ext;      //发起支付请求时传递的 eif 参数，此处原样返回。

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){

        try{

            long orderID = Long.parseLong(ext);

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

            if(this.result != 1){
                Log.e("The order not success.downjoy order:" + order + "; local order : "+orderID);
                this.renderState(false);
                return;
            }

            if(isValid(order.getChannel())){
                order.setMoney((int)(Float.valueOf(money) * 100));
                order.setChannelOrderID(this.order);
                order.setState(PayState.STATE_SUC);
                orderManager.saveOrder(order);
                SendAgent.sendCallbackToServer(this.orderManager, order);
                this.renderState(true);
            }else{
                order.setChannelOrderID(this.order);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
                this.renderState(false);
            }



        }catch (Exception e){
            e.printStackTrace();
            try {
                this.renderState(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }


    private boolean isValid(UChannel channel){
        StringBuilder sb = new StringBuilder();
        sb.append("order=").append(order)
            .append("&money=").append(money)
            .append("&mid=").append(mid)
            .append("&time=").append(time)
            .append("&result=").append(result)
            .append("&ext=").append(ext)
            .append("&key=").append(channel.getCpPayKey());

        String vCode = DownjoyMD5.MD5Encode(sb.toString());

        Log.i("the valid str :: "+sb.toString());

        Log.i("The signature is "+signature + ";the vCode is "+vCode);
        return vCode.equals(signature);
    }

    private void renderState(boolean suc) throws IOException {

        if(suc){
            this.response.getWriter().write("success");
        }else{
            this.response.getWriter().write("failure");
        }


    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}

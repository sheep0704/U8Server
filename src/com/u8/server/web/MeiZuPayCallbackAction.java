package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 魅族
 * Created by ant on 2015/9/15.
 */
@Controller
@Namespace("/pay/meizu")
public class MeiZuPayCallbackAction extends UActionSupport{

    private String notify_time  	;			//Date  Y  通知的发送时间
    private String notify_id  		;			//String(32)  Y  通知 id
    private String order_id  		;			//Long  Y  订单 id
    private String app_id  			;			//Long  Y  应用 id
    private String uid  			;			//long  Y  用户 id
    private String partner_id  		;			//long  Y  商户 id
    private String cp_order_id  	;			//String  Y  游戏订单 id
    private String product_id  		;			//String  Y  产品 id
    private String product_unit  	;			//int  N  产品单位
    private String buy_amount  		;			//int  N  购买数量
    private String product_per_price;  			//float  n  产品单价
    private String total_price  	;			//float  Y  购买总价
    private String trade_status  	;			//String  Y  交易状态：1：待支付（订单已创建）2：支付中3：已支付4：取消订单5：未知异常取消订单
    private String create_time  	;			//Date  Y  订单时间
    private String pay_time  		;			//Date  Y  支付时间
    private String pay_type  		;			//int  N  支付类型：1  不定金额充值，0  购买
    private String user_info  		;			//String  N  用户自定义信息
    private String sign  			;			//String  Y  参数签名
    private String sign_type  		;			//String  Y  签名类型，常量 md5

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long orderID = Long.parseLong(this.cp_order_id);

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

            if(order.getState() == PayState.STATE_COMPLETE) {
                Log.d("The state of the order is complete. The state is " + order.getState());
                this.renderState(true);
                return;
            }

            if(!isSignOK(channel)){
                Log.d("The sign verify failed.sign:%s;appKey:%s;orderID:%s", sign, channel.getCpPayKey(), cp_order_id);
                this.renderState(true);
                return;
            }

            if("3".equals(this.trade_status)){

                float money = Float.parseFloat(this.total_price);
                int moneyInt = (int)(money * 100);  //以分为单位

                order.setMoney(moneyInt);
                order.setChannelOrderID(this.order_id);
                order.setState(PayState.STATE_SUC);

                orderManager.saveOrder(order);

                SendAgent.sendCallbackToServer(this.orderManager, order);

            }else{
                order.setChannelOrderID(this.order_id);
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
        StringBuilder sb = new StringBuilder();
        sb.append("app_id=").append(app_id).append("&")
                .append("buy_amount=").append(buy_amount).append("&")
                .append("cp_order_id=").append(cp_order_id).append("&")
                .append("create_time=").append(create_time).append("&")
                .append("notify_id=").append(notify_id).append("&")
                .append("notify_time=").append(notify_time).append("&")
                .append("order_id=").append(order_id).append("&")
                .append("partner_id=").append(partner_id).append("&")
                .append("pay_time=").append(pay_time).append("&")
                .append("pay_type=").append(pay_type).append("&")
                .append("product_id=").append(product_id).append("&")
                .append("product_per_price=").append(product_per_price).append("&")
                .append("product_unit=").append(product_unit).append("&")
                .append("total_price=").append(total_price).append("&")
                .append("trade_status=").append(trade_status).append("&")
                .append("uid=").append(uid).append("&")
                .append("user_info=").append(channel.getCpAppSecret());

        String md5 = EncryptUtils.md5(sb.toString()).toLowerCase();

        return md5.equals(this.sign);
    }

    private void renderState(boolean suc) throws IOException {

        JSONObject json = new JSONObject();
        if(suc){
            json.put("code", "200");
            json.put("message", "发货成功");
        }else{
            json.put("code", "120014");
            json.put("message", "发货失败");
        }

        this.response.getWriter().write(json.toString());
    }

    public String getNotify_time() {

        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getCp_order_id() {
        return cp_order_id;
    }

    public void setCp_order_id(String cp_order_id) {
        this.cp_order_id = cp_order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getBuy_amount() {
        return buy_amount;
    }

    public void setBuy_amount(String buy_amount) {
        this.buy_amount = buy_amount;
    }

    public String getProduct_per_price() {
        return product_per_price;
    }

    public void setProduct_per_price(String product_per_price) {
        this.product_per_price = product_per_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}

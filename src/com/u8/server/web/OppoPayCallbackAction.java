package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.RSAUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Oppo支付回调通知接口
 * Created by ant on 2015/4/22.
 */
@Controller
@Namespace("/pay/oppo")
public class OppoPayCallbackAction extends UActionSupport{

    private String notifyId  	;				//回调通知 ID（该值使用系统为这次支付生成的订单号）String(50)
    private String partnerOrder ; 				//开发者订单号（客户端上传）  String(100)
    private String productName  ;				//商品名称（客户端上传）  String(50)
    private String productDesc  ;				//商品描述（客户端上传）  String(100)
    private String price  		;				//商品价格(以分为单位)  int
    private String count  		;				//商品数量（一般为 1）  int
    private String attach  		;				//请求支付时上传的附加参数（客户端上传）   String(200)
    private String sign  		;				//签名  String

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long orderID = Long.parseLong(partnerOrder);

            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(false, "notifyId 错误");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(false, "该订单已经被处理,或者CP订单号重复");
                return;
            }

            if(isValid(order.getChannel())){
                order.setMoney(Integer.valueOf(price));
                order.setChannelOrderID(notifyId);
                order.setState(PayState.STATE_SUC);
                orderManager.saveOrder(order);
                SendAgent.sendCallbackToServer(this.orderManager, order);
                this.renderState(true, "");
            }else{
                order.setChannelOrderID(notifyId);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
                this.renderState(false, "sign 错误");
            }


        }catch (Exception e){
            e.printStackTrace();
            try {
                this.renderState(false, "未知错误");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean isValid(UChannel channel){

        String content = getBaseString();

        return RSAUtils.verify(content, sign, channel.getCpPayKey(), "UTF-8");

    }

    private String getBaseString() {
        StringBuilder sb = new StringBuilder();
        sb.append("notifyId=").append(notifyId);
        sb.append("&partnerOrder=").append(partnerOrder);
        sb.append("&productName=").append(productName);
        sb.append("&productDesc=").append(productDesc);
        sb.append("&price=").append(price);
        sb.append("&count=").append(count);
        sb.append("&attach=").append(attach);
        return sb.toString();
    }

    private void renderState(boolean suc, String msg) throws IOException {

        StringBuilder sb = new StringBuilder();


        sb.append("result=").append(suc ? "OK" : "FAIL");
        sb.append("&").append("resultMsg=").append(msg);

        Log.d("The result to sdk is "+sb.toString());

        this.response.getWriter().write(sb.toString());


    }

    public String getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(String notifyId) {
        this.notifyId = notifyId;
    }

    public String getPartnerOrder() {
        return partnerOrder;
    }

    public void setPartnerOrder(String partnerOrder) {
        this.partnerOrder = partnerOrder;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

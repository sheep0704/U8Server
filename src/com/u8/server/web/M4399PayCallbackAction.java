package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.PayState;
import com.u8.server.data.UChannel;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;
import org.apache.http.util.TextUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Created by ant on 2015/4/22.
 */
@Controller
@Namespace("/pay/m4399")
public class M4399PayCallbackAction extends UActionSupport{

    private String orderid;             //(4399 生成的订单号) 22 位以内的字符串 唯一
    private String p_type;              //充值渠道 id
    private String uid;                 //要充值的用户 ID，my平台的用户 uid
    private String money;               //充值金额人民币，单位：元
    private String gamemoney;           //兑换的游戏币数量，兑换标准由双方共同约定，若在标准兑换比率基础上，有一些优惠或者赠送策略，可无视此数据，由应用端自行计算实际的兑换数额
    private String serverid;            //要充值的服务区号。只针对有分服的游戏有效。参数的格式为：区服 id。 例如 1 服 为 1, 11服为 11
    private String mark;                //订单号：最大长度 32 位，支持大小写字母、数字、‘|’(竖线)、‘-’（中划线）、‘_’（下划线），该字段不可为空，不可重复。
    private String roleid;              //要充值的游戏角色 id，只针对 pc端充值时，需要选择游戏角色的游戏有效。 roleid 的值由角色接口提供（见接口 2）
    private String time;                //发起请求时的时间戳
    private String sign;                //加密签名

    @Autowired
    private UOrderManager orderManager;

    @Action("payCallback")
    public void payCallback(){
        try{

            long orderID = Long.parseLong(mark);

            UOrder order = orderManager.getOrder(orderID);

            if(order == null || order.getChannel() == null){
                Log.d("The order is null or the channel is null.");
                this.renderState(1, "other_error", "cpOrderId 错误");
                return;
            }

            if(order.getState() == PayState.STATE_COMPLETE){
                Log.d("The state of the order is complete. The state is "+order.getState());
                this.renderState(3, null, "该订单已经被处理,或者CP订单号重复");
                return;
            }

            if(isValid(order.getChannel())){
                order.setMoney(Float.valueOf(money).intValue());
                order.setChannelOrderID(orderid);
                order.setState(PayState.STATE_SUC);
                orderManager.saveOrder(order);
                SendAgent.sendCallbackToServer(this.orderManager, order);
                this.renderState(2, null, money, gamemoney, "充值成功");
            }else{
                order.setChannelOrderID(orderid);
                order.setState(PayState.STATE_FAILED);
                orderManager.saveOrder(order);
                this.renderState(1, "sign_error", "sign 错误");
            }


        }catch (Exception e){
            e.printStackTrace();
            try {
                this.renderState(1, "other_error", "未知错误");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean isValid(UChannel channel) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append(orderid).append(uid).append(money).append(gamemoney);
        if(!TextUtils.isEmpty(serverid)){
            sb.append(serverid);
        }
        sb.append(channel.getCpAppSecret()).append(mark);

        if(!TextUtils.isEmpty(roleid)){
            sb.append(roleid);
        }
        sb.append(time);

        String vCode = EncryptUtils.md5(sb.toString()).toLowerCase();

        Log.i("the valid str :: "+sb.toString());
        Log.i("The sign is "+sign+"; the vCode is "+vCode);

        return vCode.equals(sign);
    }

    private void renderState(int status, String code, String msg) throws IOException {
        this.renderState(status, code, "", "", msg);
    }

    private void renderState(int status, String code, String money, String gameMoney, String msg) throws IOException {

        JSONObject json = new JSONObject();
        json.put("status", status);
        json.put("code", code);
        json.put("money", money);
        json.put("game_money", gameMoney);
        json.put("msg", msg);

        Log.d("The result to sdk is "+json.toString());

        this.response.getWriter().write(json.toString());


    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getP_type() {
        return p_type;
    }

    public void setP_type(String p_type) {
        this.p_type = p_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGamemoney() {
        return gamemoney;
    }

    public void setGamemoney(String gamemoney) {
        this.gamemoney = gamemoney;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

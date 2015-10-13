package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.constants.StateCode;
import com.u8.server.data.UOrder;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.sdk.ISDKOrderListener;
import com.u8.server.sdk.ISDKScript;
import com.u8.server.service.UOrderManager;
import com.u8.server.service.UUserManager;
import com.u8.server.utils.RSAUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/***
 * 请求获取订单号
 */
@Controller
@Namespace("/pay")
public class PayAction extends UActionSupport{

    private int userID;
    private String productName;
    private String productDesc;
    private int money;          //单位 分
    private String roleID;      //玩家在游戏服中的角色ID
    private String roleName;    //玩家在游戏服中的角色名称
    private String serverID;    //玩家所在的服务器ID
    private String serverName;  //玩家所在的服务器名称
    private String extension;

    private String sign;        //RSA签名

    @Autowired
    private UUserManager userManager;

    @Autowired
    private UOrderManager orderManager;

    private boolean isSignOK(UUser user) throws UnsupportedEncodingException {

        StringBuilder sb = new StringBuilder();
        sb.append("userID=").append(this.userID).append("&")
                .append("productName=").append(this.productName).append("&")
                .append("productDesc=").append(this.productDesc).append("&")
                .append("money=").append(this.money).append("&")
                .append("roleID=").append(this.roleID).append("&")
                .append("roleName=").append(this.roleName).append("&")
                .append("serverID=").append(this.serverID).append("&")
                .append("serverName=").append(this.serverName).append("&")
                .append("extension=").append(this.extension)
                .append(user.getGame().getAppkey());

        String encoded = URLEncoder.encode(sb.toString(), "UTF-8");

        Log.d("The encoded getOrderID sign is "+encoded);
        Log.d("The getOrderID sign is "+sign);

        return RSAUtils.verify(encoded, sign,  user.getGame().getAppRSAPubKey(), "UTF-8");

    }

    @Action("getOrderID")
    public void getOrderID(){

        try{

            UUser user = userManager.getUser(this.userID);

            if(user == null){
                renderState(StateCode.CODE_USER_NONE, null);
                return;
            }

            if(money < 0 ){
                renderState(StateCode.CODE_MONEY_ERROR, null);
                return;
            }

            if(!isSignOK(user)){
                renderState(StateCode.CODE_SIGN_ERROR, null);
                return;
            }

            final UOrder order = orderManager.generateOrder(user, money, productName, productDesc, roleID,roleName,serverID,serverName, extension);

            if(order != null){
                String scriptName = user.getChannel().getMaster().getVerifyClass();
                if(!StringUtils.isEmpty(scriptName)){
                    ISDKScript script = (ISDKScript)Class.forName(scriptName).newInstance();
                    script.onGetOrderID(user, order, new ISDKOrderListener() {
                        @Override
                        public void onSuccess(String jsonStr) {

                            JSONObject data = new JSONObject();
                            data.put("orderID", order.getOrderID());
                            data.put("extension", jsonStr);

                            Log.e("The onGetOrderID extension is "+jsonStr);

                            renderState(StateCode.CODE_AUTH_SUCCESS, data);

                        }

                        @Override
                        public void onFailed(String err) {

                            Log.e(err);

                            JSONObject data = new JSONObject();
                            data.put("orderID", order.getOrderID());
                            data.put("extension", "");
                            renderState(StateCode.CODE_AUTH_SUCCESS, data);
                        }
                    });
                }

            }


        }catch (Exception e){
            renderState(StateCode.CODE_ORDER_ERROR, null);
            Log.e(e.getMessage());
        }


    }


    private void renderState(int state, JSONObject data){
        JSONObject json = new JSONObject();
        json.put("state", state);
        json.put("data", data);

        super.renderJson(json.toString());
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

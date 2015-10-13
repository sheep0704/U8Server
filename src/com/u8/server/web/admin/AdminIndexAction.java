package com.u8.server.web.admin;

import com.u8.server.common.UActionSupport;
import com.u8.server.data.UAdmin;
import com.u8.server.log.Log;
import com.u8.server.service.UAdminManager;
import com.u8.server.utils.EncryptUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 后台管理主页
 * Created by ant on 2015/8/22.
 */
@Controller
@Namespace("/admin")
public class AdminIndexAction extends UActionSupport{

    private String username;
    private String password;

    @Autowired
    private UAdminManager adminManager;

    @Action(value = "login", results = {@Result(name = "success", location = "/WEB-INF/admin/login.jsp")})
    public String showLogin(){

        return "success";
    }

    @Action("doLogin")
    public void login(){
        try{

            Log.d("The username is "+username+";password:"+password);

            UAdmin admin = adminManager.getAdminByUsername(this.username);

            if(admin == null){
                renderState(false, "用户名错误");
                return;
            }

            if(!admin.getPassword().equals(this.password)){
                renderState(false, "用户密码错误");
                return;
            }

            this.session.put("adminName", admin.getUsername());
            renderState(true, "登录成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Action(value = "index", results = {@Result(name = "success", location = "/WEB-INF/admin/index.jsp")})
    public String index(){


        return "success";
    }

    private void renderState(boolean suc, String msg){
        JSONObject json = new JSONObject();
        json.put("state", suc? 1 : 0);
        json.put("msg", msg);
        renderText(json.toString());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

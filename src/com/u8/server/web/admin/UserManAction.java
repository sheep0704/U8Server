package com.u8.server.web.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.u8.server.common.Page;
import com.u8.server.common.UActionSupport;
import com.u8.server.data.UChannelMaster;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.service.UUserManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 用户管理
 * Created by ant on 2015/8/28.
 */
@Controller
@Namespace("/admin/users")
public class UserManAction extends UActionSupport implements ModelDriven<UUser>{


    private int page;           //当前请求的页码
    private int rows;           //当前每页显示的行数

    private UUser user;

    private int currUserID;

    @Autowired
    private UUserManager userManager;

    @Action(value = "showUsers",
            results = {@Result(name = "success", location = "/WEB-INF/admin/users.jsp")})
    public String showUsers(){

        return "success";
    }

    @Action(value = "showUserAnalytics",
            results = {@Result(name = "success", location = "/WEB-INF/admin/user_analytics.jsp")})
    public String showUserAnalytics(){

        return "success";
    }

    @Action("getAllUsers")
    public void getAllUsers(){
        try{

            long count = this.userManager.getUserCount();
            Page<UUser> currPage = this.userManager.queryPage(page, rows);

            JSONObject json = new JSONObject();
            json.put("total", count);
            JSONArray users = new JSONArray();
            for(UUser m : currPage.getResultList()){
                users.add(m.toJSON());
            }
            json.put("rows", users);

            renderJson(json.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Action("removeUser")
    public void removeUser(){
        try{
            Log.d("Curr userID is " + this.currUserID);

            UUser user = userManager.getUser(this.currUserID);

            if(user == null){
                renderState(false);
                return;
            }

            userManager.deleteUser(user);

            renderState(true);

            return;

        }catch(Exception e){
            e.printStackTrace();
        }

        renderState(false);
    }



    private void renderState(boolean suc){
        JSONObject json = new JSONObject();
        json.put("state", suc? 1 : 0);
        json.put("msg", suc? "操作成功" : "操作失败");
        renderText(json.toString());
    }


    @Override
    public UUser getModel() {
        if(this.user == null){
            this.user = new UUser();
        }
        return this.user;
    }

    public UUser getUser() {
        return user;
    }

    public void setUser(UUser user) {
        this.user = user;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrUserID() {
        return currUserID;
    }

    public void setCurrUserID(int currUserID) {
        this.currUserID = currUserID;
    }
}

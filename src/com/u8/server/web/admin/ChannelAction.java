package com.u8.server.web.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.u8.server.common.UActionSupport;
import com.u8.server.data.UChannel;
import com.u8.server.data.UChannelMaster;
import com.u8.server.data.UGame;
import com.u8.server.data.UUser;
import com.u8.server.log.Log;
import com.u8.server.service.UChannelManager;
import com.u8.server.service.UGameManager;
import com.u8.server.service.UUserManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 渠道管理
 * Created by ant on 2015/8/22.
 */
@Controller
@Namespace("/admin/channels")
public class ChannelAction extends UActionSupport implements ModelDriven<UChannel>{

    private int page;           //当前请求的页码
    private int rows;           //当前每页显示的行数

    private UChannel channel;

    private int currChannelID;

    @Autowired
    private UChannelManager channelManager;

    @Autowired
    private UUserManager userManager;

    @Action(value = "channelManage", results = {@Result(name = "success", location = "/WEB-INF/admin/channels.jsp")})
    public String channelManage(){

        return "success";
    }

    @Action("getAllChannels")
         public void getAllChannels(){
        try{

            int count = this.channelManager.getChannelCount();

            List<UChannel> lst = this.channelManager.queryPage(page, rows);


            JSONObject json = new JSONObject();
            json.put("total", count);
            JSONArray masterArray = new JSONArray();
            for(UChannel m : lst){
                masterArray.add(m.toJSON());
            }
            json.put("rows", masterArray);


            renderJson(json.toString());


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //添加或者编辑
    @Action("saveChannel")
    public void saveChannel(){

        try{

            Log.d("save.channel.info." + this.channel.toJSON().toString());
            channelManager.saveChannel(this.channel);
            renderState(true);

            return;

        }catch(Exception e){
            e.printStackTrace();
        }

        renderState(false);
    }

    @Action("removeChannel")
    public void removeChannel(){
        try{

            Log.d("Curr channelID is "+this.currChannelID);
            UChannel c = this.channelManager.queryChannel(this.currChannelID);
            if(c == null){
                renderState(false);
                return;
            }

            List<UUser> lst = this.userManager.getUsersByChannel(this.currChannelID);
            if(lst.size() > 0){
                renderState(false, "请先删除该渠道下面的所有用户数据");
                return;
            }

            this.channelManager.deleteChannel(c);

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

    private void renderState(boolean suc, String msg){
        JSONObject json = new JSONObject();
        json.put("state", suc? 1 : 0);
        json.put("msg", msg);
        renderText(json.toString());
    }


    @Override
    public UChannel getModel() {

        if(this.channel == null){
            this.channel = new UChannel();
        }

        return this.channel;
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

    public UChannel getChannel() {
        return channel;
    }

    public void setChannel(UChannel channel) {
        this.channel = channel;
    }

    public int getCurrChannelID() {
        return currChannelID;
    }

    public void setCurrChannelID(int currChannelID) {
        this.currChannelID = currChannelID;
    }
}

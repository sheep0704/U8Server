package com.u8.server.web.admin;

import com.opensymphony.xwork2.ModelDriven;
import com.u8.server.common.Page;
import com.u8.server.common.UActionSupport;
import com.u8.server.data.UOrder;
import com.u8.server.log.Log;
import com.u8.server.service.UOrderManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 订单管理
 * Created by ant on 2015/8/29.
 */
@Controller
@Namespace("/admin/orders")
public class OrderManAction extends UActionSupport implements ModelDriven<UOrder>{

    private int page;           //当前请求的页码
    private int rows;           //当前每页显示的行数

    private UOrder order;

    private long currOrderID;

    @Autowired
    private UOrderManager orderManager;

    @Action(value = "showOrders",
            results = {@Result(name = "success", location = "/WEB-INF/admin/orders.jsp")})
    public String showOrders(){

        return "success";
    }

    @Action(value = "showOrderAnalytics",
            results = {@Result(name = "success", location = "/WEB-INF/admin/order_analytics.jsp")})
    public String showOrderAnalytics(){

        return "success";
    }


    @Action("getAllOrders")
    public void getAllOrders(){
        try{

            Page<UOrder> currPage = this.orderManager.queryPage(page, rows);

            JSONObject json = new JSONObject();
            json.put("total", currPage.getTotalCount());
            JSONArray orders = new JSONArray();
            for(UOrder m : currPage.getResultList()){
                orders.add(m.toJSON());
            }
            json.put("rows", orders);

            renderJson(json.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Action("removeOrder")
    public void removeOrder(){
        try{
            Log.d("Curr orderID is " + this.currOrderID);

            UOrder order = orderManager.getOrder(this.currOrderID);

            if(order == null){
                renderState(false);
                return;
            }

            orderManager.deleteOrder(order);

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
    public UOrder getModel() {

        if(this.order == null){
            this.order = new UOrder();
        }

        return this.order;
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

    public UOrder getOrder() {
        return order;
    }

    public void setOrder(UOrder order) {
        this.order = order;
    }

    public long getCurrOrderID() {
        return currOrderID;
    }

    public void setCurrOrderID(long currOrderID) {
        this.currOrderID = currOrderID;
    }
}

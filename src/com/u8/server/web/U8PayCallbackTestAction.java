package com.u8.server.web;

import com.u8.server.common.UActionSupport;
import com.u8.server.log.Log;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

/**
 * 这个是PackServer通知游戏服的测试Action
 * Created by ant on 2015/2/9.
 */
@Controller
@Namespace("/pay/callback")
public class U8PayCallbackTestAction extends UActionSupport{



    @Action("payCallback")
    public void payCallback(){

        try{

            Log.d("Now pay callback to packserver.");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

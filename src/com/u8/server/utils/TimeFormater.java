package com.u8.server.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ant on 2015/4/24.
 */
public class TimeFormater {

    private static SimpleDateFormat FORMATER_1 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat FORMATER_2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static SimpleDateFormat FORMATER_3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format_yyyyMMddHHmmss(Date time){

        return FORMATER_1.format(time);
    }

    public static String format_yyyyMMddHHmmssSSS(Date time){
        return FORMATER_2.format(time);
    }

    public static String format_default(Date time){
        return FORMATER_3.format(time);
    }

}

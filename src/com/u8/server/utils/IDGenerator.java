package com.u8.server.utils;

import com.u8.server.cache.CacheManager;
import com.u8.server.data.UChannel;
import com.u8.server.data.UChannelMaster;
import com.u8.server.data.UGame;
import com.u8.server.service.UChannelManager;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * 这个是一个简单的唯一ID生成器
 * 在这个应用中，我们需要生成appID, channelID，orderID等
 */
public class IDGenerator {

    private static IDGenerator instance;

    private AtomicInteger currAppID;
    private AtomicInteger currMasterID;
    private AtomicInteger currChannelID;

    private AtomicInteger currOrderSequence = new AtomicInteger(1);

    private IDGenerator(){

        loadCurrMaxID();

    }

    public static IDGenerator getInstance(){
        if(instance == null){
            instance = new IDGenerator();
        }

        return instance;
    }

    private void loadCurrMaxID(){
        int maxID = 0;

        for(UGame game : CacheManager.getInstance().getGameList()){
            if(maxID < game.getAppID()){
                maxID = game.getAppID();
            }
        }

        this.currAppID = new AtomicInteger(maxID);

        maxID = 0;
        for(UChannelMaster master : CacheManager.getInstance().getMasterList()){
            if(maxID < master.getMasterID()){
                maxID = master.getMasterID();
            }
        }

        this.currMasterID = new AtomicInteger(maxID);

        maxID = 0;
        for(UChannel channel : CacheManager.getInstance().getChannelList()){
            if(maxID < channel.getChannelID()){
                maxID = channel.getChannelID();
            }
        }

        this.currChannelID = new AtomicInteger(maxID);
    }

    public int nextAppID(){


        return this.currAppID.incrementAndGet();
    }

    public int nextMasterID(){
        return this.currMasterID.incrementAndGet();
    }

    public int nextChannelID(){

        return this.currChannelID.incrementAndGet();
    }

    public long nextOrderID(){

        Calendar can = Calendar.getInstance();
        int year = can.get(Calendar.YEAR) - 2013;
        int month = can.get(Calendar.MONTH) + 1;
        int day = can.get(Calendar.DAY_OF_MONTH);
        int hour = can.get(Calendar.HOUR_OF_DAY);
        int min = can.get(Calendar.MINUTE);
        int sec = can.get(Calendar.SECOND);

        long orderId = year;
        orderId = orderId << 4 | month;
        orderId = orderId << 5 | day;
        orderId = orderId << 5 | hour;
        orderId = orderId << 6 | min;
        orderId = orderId << 6 | sec;
        orderId = orderId << 32| this.currOrderSequence.getAndIncrement();

        return orderId;
    }
}

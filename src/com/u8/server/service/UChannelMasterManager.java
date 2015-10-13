package com.u8.server.service;

import com.u8.server.cache.CacheManager;
import com.u8.server.dao.UChannelMasterDao;
import com.u8.server.data.UChannel;
import com.u8.server.data.UChannelMaster;
import com.u8.server.utils.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 */

@Service("channelMasterManager")
public class UChannelMasterManager {

    @Autowired
    private UChannelMasterDao channelMasterDao;

    public void saveChannelMaster(UChannelMaster master){

        if(master == null){
            return;
        }

        if(master.getMasterID() == null || master.getMasterID() <= 0){
            master.setMasterID(IDGenerator.getInstance().nextMasterID());
        }

        CacheManager.getInstance().saveMaster(master);
        channelMasterDao.saveChannelMaster(master);
    }

    //删除渠道商
    public void deleteChannelMaster(UChannelMaster master){
        if(master == null){
            return;
        }

        CacheManager.getInstance().removeMaster(master.getMasterID());
        channelMasterDao.delete(master);
    }

    public UChannelMaster generateMaster(String name, String masterName, String suffix, String authUrl, String paycallbackUrl, String verifyClass){

        return generateMaster(name,masterName,suffix,authUrl,paycallbackUrl,verifyClass, "");
    }

    public UChannelMaster generateMaster(String name, String masterName, String suffix, String authUrl, String paycallbackUrl, String verifyClass, String orderUrl){
        UChannelMaster master = new UChannelMaster();
        master.setSdkName(name);
        master.setMasterName(masterName);
        master.setNameSuffix(suffix);
        master.setAuthUrl(authUrl);
        master.setPayCallbackUrl(paycallbackUrl);
        master.setVerifyClass(verifyClass);
        master.setOrderUrl(orderUrl);

        saveChannelMaster(master);

        return master;
    }

    public UChannelMaster queryChannelMaster(int masterID){

        return CacheManager.getInstance().getMaster(masterID);
    }

    //获取当前指定master下的所有渠道
    public List<UChannel> queryChannels(int masterID){

        List<UChannel> channels = CacheManager.getInstance().getChannelList();
        List<UChannel> lst = new ArrayList<UChannel>();
        for(UChannel c : channels){
            if(c.getMasterID() == masterID){
                lst.add(c);
            }
        }
        return lst;
    }

    //获取所有渠道商信息
    public List<UChannelMaster> queryAll(){

        return CacheManager.getInstance().getMasterList();
    }

    public int getMasterCount(){

        return CacheManager.getInstance().getMasterList().size();
    }


    //分页查找
    public List<UChannelMaster> queryPage(int currPage, int num){

        List<UChannelMaster> masters = CacheManager.getInstance().getMasterList();

        Collections.sort(masters, new Comparator<UChannelMaster>() {
            @Override
            public int compare(UChannelMaster o1, UChannelMaster o2) {
                return o1.getMasterID() - o2.getMasterID();
            }
        });

        int fromIndex = (currPage-1) * num;

        if(fromIndex >= masters.size()){

            return null;
        }

        int endIndex = Math.min(fromIndex+num, masters.size());

        return masters.subList(fromIndex, endIndex);
    }
}

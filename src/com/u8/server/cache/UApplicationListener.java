package com.u8.server.cache;

import com.u8.server.dao.UChannelDao;
import com.u8.server.dao.UChannelMasterDao;
import com.u8.server.dao.UGameDao;
import com.u8.server.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 在Spring启动的时候，加载需要缓存的数据
 */
@Repository
@Transactional(readOnly = true)
public class UApplicationListener implements ApplicationListener<ApplicationEvent>{

    @Autowired
    private UGameDao gameDao;
    @Autowired
    private UChannelDao channelDao;
    @Autowired
    private UChannelMasterDao channelMasterDao;

    private boolean loaded = false;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        try{

            if(!loaded){

                CacheManager.getInstance().loadGameData(gameDao.findAll());
                CacheManager.getInstance().loadMasterData(channelMasterDao.findAll());
                CacheManager.getInstance().loadChannelData(channelDao.findAll());

                loaded = true;
            }

        }catch (Exception e){
            Log.e("Load Data on server inited error.", e);
        }



    }
}

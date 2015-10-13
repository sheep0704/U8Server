package com.u8.server.dao;

import com.u8.server.common.UHibernateTemplate;
import com.u8.server.data.UChannelMaster;
import org.springframework.stereotype.Repository;

/**
 * 渠道商数据访问类
 */
@Repository("channelMasterDao")
public class UChannelMasterDao extends UHibernateTemplate<UChannelMaster, Integer> {

    public void saveChannelMaster(UChannelMaster master){
        super.save(master);
    }

    public UChannelMaster queryChannelMaster(int channelID){

        return super.get(channelID);
    }

}

package com.u8.server.dao;

import com.u8.server.common.UHibernateTemplate;
import com.u8.server.data.UGame;
import org.springframework.stereotype.Repository;


/**
 * 游戏对象数据访问类
 */
@Repository("gameDao")
public class UGameDao extends UHibernateTemplate<UGame, Integer>{


    public void saveGame(UGame game){
        super.save(game);
    }

    public UGame queryGame(int appID){

        return super.get(appID);
    }

}

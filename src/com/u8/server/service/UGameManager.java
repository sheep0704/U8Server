package com.u8.server.service;

import com.u8.server.cache.CacheManager;
import com.u8.server.dao.UGameDao;
import com.u8.server.data.UChannel;
import com.u8.server.data.UChannelMaster;
import com.u8.server.data.UGame;
import com.u8.server.utils.IDGenerator;
import com.u8.server.utils.RSAUtils;
import com.u8.server.utils.UGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("gameManager")
public class UGameManager {

    @Autowired
    private UGameDao gameDao;

    public UGame generateGame(String name, String payCallback, String payCallbackDebug){

        UGame game = new UGame();
        int appID = IDGenerator.getInstance().nextAppID();
        long currTime = System.currentTimeMillis();
        game.setAppID(appID);
        game.setAppkey(UGenerator.generateAppKey(appID, currTime));
        game.setName(name);
        game.setPayCallbackDebug(payCallback);
        game.setPayCallback(payCallbackDebug);

        try {
            Map<String, Object> keys = RSAUtils.generateKeys();
            game.setAppRSAPubKey(RSAUtils.getPublicKey(keys));
            game.setAppRSAPriKey(RSAUtils.getPrivateKey(keys));
        } catch (Exception e) {
            e.printStackTrace();
        }

        saveGame(game);


        return game;
    }


    public void saveGame(UGame game){
        CacheManager.getInstance().saveGame(game);
        gameDao.save(game);
    }

    public void deleteGame(UGame game){

        if(game == null){
            return;
        }

        CacheManager.getInstance().removeGame(game.getAppID());
        gameDao.delete(game);

    }

    public UGame queryGame(int appID){

        return CacheManager.getInstance().getGame(appID);
    }

    public List<UGame> queryAllGames(){

        return CacheManager.getInstance().getGameList();
    }

    public int getGameCount(){

        return CacheManager.getInstance().getGameList().size();
    }

    public List<UChannel> queryChannels(int appID){
        List<UChannel> lst = CacheManager.getInstance().getChannelList();

        List<UChannel> result = new ArrayList<UChannel>();
        for(UChannel c : lst){
            if(c.getAppID() == appID){
                result.add(c);
            }
        }

        return result;
    }

    //分页查找
    public List<UGame> queryPage(int currPage, int num){

        List<UGame> masters = CacheManager.getInstance().getGameList();

        Collections.sort(masters, new Comparator<UGame>() {
            @Override
            public int compare(UGame o1, UGame o2) {
                return o1.getAppID() - o2.getAppID();
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

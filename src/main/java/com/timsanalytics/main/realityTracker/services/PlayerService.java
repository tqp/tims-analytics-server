package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.dao.PlayerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<Player> getPlayerListBySeasonGuid(String seriesGuid) {
        return this.playerDao.getPlayerListBySeasonGuid(seriesGuid);
    }
}

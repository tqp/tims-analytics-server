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

    public Player getPlayerDetail(String playerGuid) {
        return this.playerDao.getPlayerDetail(playerGuid);
    }

    public List<Player> getPlayerListFiltered(String seriesGuid, String contestantGuid) {
        return this.playerDao.getPlayerListFiltered(seriesGuid, contestantGuid);
    }

    public List<Player> getPlayerListByContestantGuid(String contestantGuid) {
        return this.playerDao.getPlayerListByContestantGuid(contestantGuid);
    }

    public Player updatePlayer(Player player) {
        return this.playerDao.updatePlayer(player);
    }
}

package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.main.realityTracker.beans.ListItem;
import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.beans.Season;
import com.timsanalytics.main.realityTracker.dao.PlayerDao;
import com.timsanalytics.utils.GenerateUuidService;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final PlayerDao playerDao;
    private final GenerateUuidService generateUuidService;
    private final PrintObjectService printObjectService;

    @Autowired
    public PlayerService(PlayerDao playerDao,
                         GenerateUuidService generateUuidService,
                         PrintObjectService printObjectService) {
        this.playerDao = playerDao;
        this.generateUuidService = generateUuidService;
        this.printObjectService = printObjectService;
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

    // Contestant-Season Add/Remove

    public List<Player> getCurrentSeasonsByContestantGuid(String contestantGuid) {
        return this.playerDao.getCurrentSeasonsByContestantGuid(contestantGuid);
    }

    public List<Player> getAvailableSeasonsByContestantGuid(String contestantGuid) {
        return this.playerDao.getAvailableSeasonsByContestantGuid(contestantGuid);
    }

    public List<Player> addSeasonsToContestant(String contestantGuid, List<ListItem> itemsToAdd) {
        // Because MySQL doesn't have a MERGE query like Oracle, we are forced to use MySQL's
        // ON DUPLICATE KEY UPDATE query. To make this work for us, we first have to get the key from the
        // association table, if one exists. If the key already exists in the table, the query will update
        // it with a status of 'Active'. If it doesn't exist, the query will create it.
        List<Player> playerList = this.createPlayerListToAddSeasonToContestant(contestantGuid, itemsToAdd);
        int[] recordsUpdated = this.playerDao.addPlayer(playerList);
        return playerList;
    }

    private List<Player> createPlayerListToAddSeasonToContestant(String contestantGuid, List<ListItem> itemsToAdd) {
        return itemsToAdd.stream()
                .map(item -> {
                    Player existingPlayer = this.playerDao.getPlayerByPlayerGuid(contestantGuid, item.getGuid());
                    Player player = new Player();
                    player.setPlayerGuid(existingPlayer != null ? existingPlayer.getPlayerGuid() : this.generateUuidService.GenerateUuid());
                    player.setContestantGuid(contestantGuid);
                    player.setSeasonGuid(item.getGuid());
                    return player;
                })
                .collect(Collectors.toList());
    }

    public List<ListItem> removeSeasonsFromContestant(String contestantGuid, List<ListItem> itemsToRemove) {
        int[] recordsUpdated = this.playerDao.removeSeasonsFromContestant(contestantGuid, itemsToRemove);
        return itemsToRemove;
    }

    // Season-Contestant Add/Remove

    public List<Player> getCurrentContestantsBySeasonGuid(String contestantGuid) {
        return this.playerDao.getCurrentContestantsBySeasonGuid(contestantGuid);
    }

    public List<Player> getAvailableContestantsBySeasonGuid(String contestantGuid) {
        return this.playerDao.getAvailableContestantsBySeasonGuid(contestantGuid);
    }

    public List<Player> addContestantsToSeason(String seasonGuid, List<ListItem> itemsToAdd) {
        // Because MySQL doesn't have a MERGE query like Oracle, we are forced to use MySQL's
        // ON DUPLICATE KEY UPDATE query. To make this work for us, we first have to get the key from the
        // association table, if one exists. If the key already exists in the table, the query will update
        // it with a status of 'Active'. If it doesn't exist, the query will create it.
        List<Player> playerList = this.createPlayerListToAddContestantToSeason(seasonGuid, itemsToAdd);
        int[] recordsUpdated = this.playerDao.addPlayer(playerList);
        return playerList;
    }

    private List<Player> createPlayerListToAddContestantToSeason(String seasonGuid, List<ListItem> itemsToAdd) {
        return itemsToAdd.stream()
                .map(item -> {
                    Player existingPlayer = this.playerDao.getPlayerByPlayerGuid(seasonGuid, item.getGuid());
                    Player player = new Player();
                    player.setPlayerGuid(existingPlayer != null ? existingPlayer.getPlayerGuid() : this.generateUuidService.GenerateUuid());
                    player.setSeasonGuid(seasonGuid);
                    player.setContestantGuid(item.getGuid());
                    return player;
                })
                .collect(Collectors.toList());
    }

    public List<ListItem> removeContestantsFromSeason(String contestantGuid, List<ListItem> itemsToRemove) {
        int[] recordsUpdated = this.playerDao.removeContestantsFromSeason(contestantGuid, itemsToRemove);
        return itemsToRemove;
    }
}

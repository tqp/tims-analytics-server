package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public PlayerDao(JdbcTemplate mySqlAuthJdbcTemplate, GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }
    public List<Player> getPlayerListBySeasonGuid(String seriesGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      PLAYER.SEASON_GUID,\n");
        query.append("      PLAYER.CONTESTANT_GUID,\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.CONTESTANT ON PLAYER.CONTESTANT_GUID = CONTESTANT.CONTESTANT_GUID\n");
        query.append("  WHERE\n");
        query.append("      PLAYER_STATUS = 'Active'\n");
        query.append("      AND SEASON_GUID = ?\n");
        query.append("  ORDER BY\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seriesGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setGuid(rs.getString("PLAYER_GUID"));
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setContestantGuid(rs.getString("CONTESTANT_GUID"));
                item.setContestantLastName(rs.getString("CONTESTANT_LAST_NAME"));
                item.setContestantFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
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

    public Player getPlayerDetail(String playerGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      PLAYER.PLAYER_OCCUPATION,\n");
        query.append("      PLAYER.PLAYER_AGE_ON_ENTRY,\n");
        query.append("      PLAYER.SEASON_GUID,\n");
        query.append("      PLAYER.CONTESTANT_GUID,\n");
        query.append("      SERIES.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION,\n");
        query.append("      SEASON.SEASON_NAME,\n");
        query.append("      SEASON.SEASON_ABBREVIATION,\n");
        query.append("      CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.CONTESTANT ON PLAYER.CONTESTANT_GUID = CONTESTANT.CONTESTANT_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SEASON ON PLAYER.SEASON_GUID = SEASON.SEASON_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      PLAYER_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{playerGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setGuid(rs.getString("PLAYER_GUID"));
                item.setOccupation(rs.getString("PLAYER_OCCUPATION"));
                int ageOnEntry = rs.getInt("PLAYER_AGE_ON_ENTRY");
                item.setAgeOnEntry(rs.wasNull() ? null : ageOnEntry);
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setContestantGuid(rs.getString("CONTESTANT_GUID"));
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
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

    public List<Player> getPlayerListFiltered(String seriesGuid, String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      SERIES.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION,\n");
        query.append("      PLAYER.SEASON_GUID,\n");
        query.append("      SEASON.SEASON_NAME,\n");
        query.append("      SEASON.SEASON_ABBREVIATION,\n");
        query.append("      PLAYER.CONTESTANT_GUID,\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.CONTESTANT ON PLAYER.CONTESTANT_GUID = CONTESTANT.CONTESTANT_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SEASON ON PLAYER.SEASON_GUID = SEASON.SEASON_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      PLAYER_STATUS = 'Active'\n");
        query.append("      AND PLAYER.SEASON_GUID LIKE ?\n");
        query.append("      AND PLAYER.CONTESTANT_GUID LIKE ?\n");
        query.append("  ORDER BY\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        seriesGuid = (seriesGuid != null) ? seriesGuid : "%";
        contestantGuid = (contestantGuid != null) ? contestantGuid : "%";
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seriesGuid, contestantGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setGuid(rs.getString("PLAYER_GUID"));
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
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

    public List<Player> getPlayerListByContestantGuid(String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      SERIES.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION,\n");
        query.append("      PLAYER.SEASON_GUID,\n");
        query.append("      SEASON.SEASON_NAME,\n");
        query.append("      SEASON.SEASON_ABBREVIATION\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SEASON ON PLAYER.SEASON_GUID = SEASON.SEASON_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{contestantGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setGuid(rs.getString("PLAYER_GUID"));
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
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

    public Player updatePlayer(Player player) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("  SET\n");
        query.append("      PLAYER.PLAYER_OCCUPATION = ?,\n");
        query.append("      PLAYER.PLAYER_AGE_ON_ENTRY = ?\n");
        query.append("  WHERE\n");
        query.append("      PLAYER.PLAYER_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, player.getOccupation());
                        ps.setLong(2, player.getAgeOnEntry());
                        ps.setString(3, player.getGuid());
                        return ps;
                    }
            );
            return this.getPlayerDetail(player.getGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

package com.timsanalytics.apps.realityTracker.dao;

import com.timsanalytics.apps.realityTracker.beans.ListItem;
import com.timsanalytics.apps.realityTracker.beans.Player;
import com.timsanalytics.utils.GenerateUuidService;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class PlayerDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;
    private final PrintObjectService printObjectService;

    @Autowired
    public PlayerDao(JdbcTemplate mySqlAuthJdbcTemplate,
                     GenerateUuidService generateUuidService,
                     PrintObjectService printObjectService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
        this.printObjectService = printObjectService;
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
                item.setPlayerGuid(rs.getString("PLAYER_GUID"));
                item.setPlayerOccupation(rs.getString("PLAYER_OCCUPATION"));
                int ageOnEntry = rs.getInt("PLAYER_AGE_ON_ENTRY");
                item.setPlayerAgeOnEntry(rs.wasNull() ? null : ageOnEntry);
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
        query.append("      PLAYER.STATUS = 'Active'\n");
        query.append("      AND PLAYER.SEASON_GUID LIKE ?\n");
        query.append("      AND PLAYER.CONTESTANT_GUID LIKE ?\n");
        query.append("  ORDER BY\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SEASON.SEASON_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        seriesGuid = (seriesGuid != null) ? seriesGuid : "%";
        contestantGuid = (contestantGuid != null) ? contestantGuid : "%";
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seriesGuid, contestantGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setPlayerGuid(rs.getString("PLAYER_GUID"));
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
                item.setPlayerGuid(rs.getString("PLAYER_GUID"));
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
                        ps.setString(1, player.getPlayerOccupation());
                        ps.setLong(2, player.getPlayerAgeOnEntry());
                        ps.setString(3, player.getPlayerGuid());
                        return ps;
                    }
            );
            return this.getPlayerDetail(player.getPlayerGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // Contestant-Season Add/Remove

    public List<Player> getCurrentSeasonsByContestantGuid(String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      SERIES.SERIES_GUID,\n");
        query.append("      SERIES_NAME,\n");
        query.append("      SERIES_ABBREVIATION,\n");
        query.append("      PLAYER.SEASON_GUID,\n");
        query.append("      SEASON_NAME,\n");
        query.append("      SEASON_ABBREVIATION\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SEASON ON PLAYER.SEASON_GUID = SEASON.SEASON_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      PLAYER.STATUS = 'Active' \n");
        query.append("      AND PLAYER.CONTESTANT_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{contestantGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setPlayerGuid(rs.getString("PLAYER_GUID"));
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

    public List<Player> getAvailableSeasonsByContestantGuid(String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT DISTINCT\n");
        query.append("      SEASON.SERIES_GUID,\n");
        query.append("      SERIES_NAME,\n");
        query.append("      SERIES_ABBREVIATION,\n");
        query.append("      SEASON_GUID,\n");
        query.append("      SEASON_NAME,\n");
        query.append("      SEASON_ABBREVIATION\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      SEASON.STATUS NOT IN ('Deleted')\n");
        query.append("      AND SEASON_GUID NOT IN\n");
        query.append("      (\n");
        query.append("          SELECT DISTINCT\n");
        query.append("              PLAYER.SEASON_GUID\n");
        query.append("          FROM\n");
        query.append("              REALITY_TRACKER.PLAYER\n");
        query.append("          WHERE\n");
        query.append("              PLAYER.CONTESTANT_GUID = ?\n");
        query.append("              AND PLAYER.STATUS = 'Active'\n");
        query.append("      )\n");
        query.append("  ORDER BY\n");
        query.append("      SERIES_NAME,\n");
        query.append("      SEASON_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{contestantGuid}, (rs, rowNum) -> {
                Player item = new Player();
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

    public int[] addSeasonsToContestants(List<Player> playerList) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      (\n");
        query.append("          PLAYER_GUID,\n");
        query.append("          CONTESTANT_GUID,\n");
        query.append("          SEASON_GUID,\n");
        query.append("          STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        query.append("      ON DUPLICATE KEY UPDATE\n");
        query.append("          STATUS = 'Active'\n");
        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, playerList.get(i).getPlayerGuid());
                            ps.setString(2, playerList.get(i).getContestantGuid());
                            ps.setString(3, playerList.get(i).getSeasonGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return playerList.size();
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public int[] removeSeasonsFromContestant(String contestantGuid, List<ListItem> itemsToRemove) {
        // Define SQL for batch
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.PLAYER A\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Removed'\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT_GUID = ?\n");
        query.append("      AND SEASON_GUID = ?\n");
        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, contestantGuid);
                            ps.setString(2, itemsToRemove.get(i).getGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return itemsToRemove.size();
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Player getPlayerByPlayerGuid(String contestantGuid, String seasonGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      CONTESTANT_GUID,\n");
        query.append("      SEASON_GUID,\n");
        query.append("      STATUS\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT_GUID = ?\n");
        query.append("      AND SEASON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("contestantGuid: " + contestantGuid);
        this.logger.trace("seasonGuid: " + seasonGuid);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{contestantGuid, seasonGuid},
                    (rs, rowNum) -> {
                        Player item = new Player();
                        item.setPlayerGuid(rs.getString("PLAYER_GUID"));
                        item.setContestantGuid(rs.getString("CONTESTANT_GUID"));
                        item.setSeasonGuid(rs.getString("SEASON_GUID"));
                        item.setStatus(rs.getString("STATUS"));
                        return item;
                    });
        } catch (EmptyResultDataAccessException e) {
//            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // Season-Contestant Add/Remove

    public List<Player> getCurrentContestantsBySeasonGuid(String seasonGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PLAYER_GUID,\n");
        query.append("      PLAYER.CONTESTANT_GUID,\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      LEFT JOIN REALITY_TRACKER.CONTESTANT ON PLAYER.CONTESTANT_GUID = CONTESTANT.CONTESTANT_GUID\n");
        query.append("  WHERE\n");
        query.append("      PLAYER.STATUS = 'Active'\n");
        query.append("      AND PLAYER.SEASON_GUID = ?\n");
        query.append("  ORDER BY\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seasonGuid}, (rs, rowNum) -> {
                Player item = new Player();
                item.setPlayerGuid(rs.getString("PLAYER_GUID"));
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

    public List<Player> getAvailableContestantsBySeasonGuid(String seasonGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT DISTINCT\n");
        query.append("      CONTESTANT.CONTESTANT_GUID,\n");
        query.append("      CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.CONTESTANT\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT.STATUS = 'Active'\n");
        query.append("      AND CONTESTANT_GUID NOT IN\n");
        query.append("      (\n");
        query.append("          SELECT DISTINCT\n");
        query.append("              PLAYER.CONTESTANT_GUID\n");
        query.append("          FROM\n");
        query.append("              REALITY_TRACKER.PLAYER\n");
        query.append("          WHERE\n");
        query.append("              PLAYER.SEASON_GUID = ?\n");
        query.append("              AND PLAYER.STATUS = 'Active'\n");
        query.append("      )\n");
        query.append("  ORDER BY\n");
        query.append("      CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seasonGuid}, (rs, rowNum) -> {
                Player item = new Player();
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

    public int[] addPlayer(List<Player> playerList) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      REALITY_TRACKER.PLAYER\n");
        query.append("      (\n");
        query.append("          PLAYER_GUID,\n");
        query.append("          CONTESTANT_GUID,\n");
        query.append("          SEASON_GUID,\n");
        query.append("          STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        query.append("      ON DUPLICATE KEY UPDATE\n");
        query.append("          STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, playerList.get(i).getPlayerGuid());
                            ps.setString(2, playerList.get(i).getContestantGuid());
                            ps.setString(3, playerList.get(i).getSeasonGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return playerList.size();
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public int[] removeContestantsFromSeason(String seasonGuid, List<ListItem> itemsToRemove) {
        // Define SQL for batch
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.PLAYER A\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Removed'\n");
        query.append("  WHERE\n");
        query.append("      SEASON_GUID = ?\n");
        query.append("      AND CONTESTANT_GUID = ?\n");
        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, seasonGuid);
                            ps.setString(2, itemsToRemove.get(i).getGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return itemsToRemove.size();
                        }
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

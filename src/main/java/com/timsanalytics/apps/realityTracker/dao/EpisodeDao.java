package com.timsanalytics.apps.realityTracker.dao;

import com.timsanalytics.apps.realityTracker.beans.Episode;
import com.timsanalytics.apps.realityTracker.beans.Season;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.utils.GenerateUuidService;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class EpisodeDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;
    private final PrintObjectService printObjectService;

    @Autowired
    public EpisodeDao(JdbcTemplate mySqlAuthJdbcTemplate,
                     GenerateUuidService generateUuidService,
                     PrintObjectService printObjectService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
        this.printObjectService = printObjectService;
    }

    public Episode createEpisode(Episode episode) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      REALITY_TRACKER.EPISODE\n");
        query.append("      (\n");
        query.append("          EPISODE_GUID,\n");
        query.append("          SEASON_GUID,\n");
        query.append("          EPISODE_NAME,\n");
        query.append("          STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        episode.setEpisodeGuid(this.generateUuidService.GenerateUuid());
                        this.logger.trace("New Episode GUID: " + episode.getEpisodeGuid());
                        ps.setString(1, episode.getEpisodeGuid());
                        ps.setString(2, episode.getSeasonGuid());
                        ps.setString(3, episode.getEpisodeName());
                        return ps;
                    }
            );
            return this.getEpisodeDetail(episode.getEpisodeGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<Episode> getEpisodeListFiltered(String seasonGuid) {
        seasonGuid = (seasonGuid != null) ? seasonGuid : "%";
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      EPISODE_GUID,\n");
        query.append("      EPISODE_NAME,\n");
        query.append("      SEASON_GUID\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.EPISODE\n");
        query.append("  WHERE\n");
        query.append("      SEASON_GUID LIKE ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("seasonGuid: " + seasonGuid);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seasonGuid}, (rs, rowNum) -> {
                Episode item = new Episode();
                item.setEpisodeGuid(rs.getString("EPISODE_GUID"));
                item.setEpisodeName(rs.getString("EPISODE_NAME"));
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
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

    public Episode getEpisodeDetail(String episodeGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      SERIES.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION,\n");
        query.append("      SEASON.SEASON_GUID,\n");
        query.append("      SEASON.SEASON_NAME,\n");
        query.append("      SEASON.SEASON_ABBREVIATION,\n");
        query.append("      EPISODE_GUID,\n");
        query.append("      EPISODE_NAME,\n");
        query.append("      EPISODE_ORIGINAL_AIR_DATE,\n");
        query.append("      EPISODE_NUMBER_IN_SEASON,\n");
        query.append("      EPISODE_NUMBER_IN_SERIES,\n");
        query.append("      EPISODE_COMMENTS,\n");
        query.append("      EPISODE.STATUS\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.EPISODE\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SEASON ON SEASON.SEASON_GUID = EPISODE.SEASON_GUID\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SERIES.SERIES_GUID = SEASON.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      EPISODE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{episodeGuid}, (rs, rowNum) -> {
                Episode item = new Episode();
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
                item.setEpisodeGuid(rs.getString("EPISODE_GUID"));
                item.setEpisodeName(rs.getString("EPISODE_NAME"));
                item.setEpisodeDate(rs.getString("EPISODE_ORIGINAL_AIR_DATE"));
                item.setEpisodeNumberInSeason(rs.getInt("EPISODE_NUMBER_IN_SEASON"));
                item.setEpisodeNumberInSeries(rs.getInt("EPISODE_NUMBER_IN_SERIES"));
                item.setEpisodeComments(rs.getString("EPISODE_COMMENTS"));
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

    public Episode updateEpisode(Episode episode) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.EPISODE\n");
        query.append("  SET\n");
        query.append("      EPISODE.EPISODE_NAME = ?,\n");
        query.append("      EPISODE.EPISODE_ORIGINAL_AIR_DATE = ?,\n");
        query.append("      EPISODE.EPISODE_NUMBER_IN_SEASON = ?,\n");
        query.append("      EPISODE.EPISODE_NUMBER_IN_SERIES = ?,\n");
        query.append("      EPISODE.EPISODE_COMMENTS = ?\n");
        query.append("  WHERE\n");
        query.append("      EPISODE.EPISODE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, episode.getEpisodeName());
                        ps.setString(2, episode.getEpisodeDate());
                        ps.setInt(3, episode.getEpisodeNumberInSeason());
                        ps.setInt(4, episode.getEpisodeNumberInSeries());
                        ps.setString(5, episode.getEpisodeComments());
                        ps.setString(6, episode.getEpisodeGuid());
                        return ps;
                    }
            );
            return this.getEpisodeDetail(episode.getEpisodeGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public KeyValue deleteEpisode(String episodeGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.EPISODE\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      EPISODE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("EPISODE_GUID=" + episodeGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, episodeGuid);
                        return ps;
                    }
            );
            return new KeyValue("episodeGuid", episodeGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

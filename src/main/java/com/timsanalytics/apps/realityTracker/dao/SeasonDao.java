package com.timsanalytics.apps.realityTracker.dao;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.apps.realityTracker.beans.Season;
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
public class SeasonDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public SeasonDao(JdbcTemplate mySqlAuthJdbcTemplate, GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }

    public Season createSeason(Season season) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("      (\n");
        query.append("          SEASON_GUID,\n");
        query.append("          SERIES_GUID,\n");
        query.append("          SEASON_NAME,\n");
        query.append("          SEASON_ABBREVIATION,\n");
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

        this.logger.trace("getName:\n" + season.getSeasonName());

        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        season.setSeasonGuid(this.generateUuidService.GenerateUuid());
                        this.logger.trace("New Season GUID: " + season.getSeasonGuid());
                        ps.setString(1, season.getSeasonGuid());
                        ps.setString(2, season.getSeriesGuid());
                        ps.setString(3, season.getSeasonName());
                        return ps;
                    }
            );
            return this.getSeasonDetail(season.getSeasonGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Season getSeasonDetail(String seasonGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      SEASON_GUID,\n");
        query.append("      SEASON_NAME,\n");
        query.append("      SEASON_ABBREVIATION,\n");
        query.append("      SEASON_START_DATE,\n");
        query.append("      SEASON.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      SEASON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{seasonGuid}, (rs, rowNum) -> {
                Season item = new Season();
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeriesAbbreviation(rs.getString("SEASON_ABBREVIATION"));
                item.setSeasonAbbreviation(rs.getString("SERIES_ABBREVIATION"));
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeasonStartDate(rs.getDate("SEASON_START_DATE"));
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

    public List<Season> getSeasonListBySeriesGuid(String seriesGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      SEASON_GUID,\n");
        query.append("      SEASON_NAME,\n");
        query.append("      SEASON_ABBREVIATION,\n");
        query.append("      SEASON_START_DATE,\n");
        query.append("      SEASON.SERIES_GUID,\n");
        query.append("      SERIES.SERIES_NAME,\n");
        query.append("      SERIES.SERIES_ABBREVIATION\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("      LEFT JOIN REALITY_TRACKER.SERIES ON SEASON.SERIES_GUID = SERIES.SERIES_GUID\n");
        query.append("  WHERE\n");
        query.append("      SEASON.STATUS = 'Active'\n");
        query.append("      AND SEASON.SERIES_GUID = ?\n");
        query.append("  ORDER BY\n");
        query.append("      SEASON_START_DATE DESC,\n");
        query.append("      SEASON_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seriesGuid}, (rs, rowNum) -> {
                Season item = new Season();
                item.setSeasonGuid(rs.getString("SEASON_GUID"));
                item.setSeasonName(rs.getString("SEASON_NAME"));
                item.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
                item.setSeasonStartDate(rs.getDate("SEASON_START_DATE"));
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                item.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
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

    public Season updateSeason(Season season) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("  SET\n");
        query.append("      SEASON.SEASON_NAME = ?,\n");
        query.append("      SEASON.SEASON_ABBREVIATION = ?\n");
        query.append("  WHERE\n");
        query.append("      SEASON.SEASON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, season.getSeasonName());
                        ps.setString(2, season.getSeasonAbbreviation());
                        ps.setString(3, season.getSeasonGuid());
                        return ps;
                    }
            );
            return this.getSeasonDetail(season.getSeasonGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public KeyValue deleteSeason(String seasonGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      SEASON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("SEASON_GUID=" + seasonGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, seasonGuid);
                        return ps;
                    }
            );
            return new KeyValue("seasonGuid", seasonGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

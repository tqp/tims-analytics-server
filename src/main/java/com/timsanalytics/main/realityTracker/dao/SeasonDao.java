package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Season;
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
        query.append("          SEASON.SEASON_GUID,\n");
        query.append("          SEASON.SERIES_GUID,\n");
        query.append("          SEASON.SEASON_NAME,\n");
        query.append("          SEASON.STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());

        this.logger.trace("getName:\n" + season.getName());

        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        season.setGuid(this.generateUuidService.GenerateUuid());
                        this.logger.trace("New Season GUID: " + season.getGuid());
                        ps.setString(1, season.getGuid());
                        ps.setString(2, season.getSeriesGuid());
                        ps.setString(3, season.getName());
                        return ps;
                    }
            );
            return this.getSeasonDetail(season.getGuid());
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
        query.append("      SERIES_GUID,\n");
        query.append("      SEASON_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("  WHERE\n");
        query.append("      SEASON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{seasonGuid}, new SeasonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<Season> getSeriesSeasonList(String seriesGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      SEASON_GUID,\n");
        query.append("      SERIES_GUID,\n");
        query.append("      SEASON_NAME\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.SEASON\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("      AND SERIES_GUID = ?\n");
        query.append("  ORDER BY\n");
        query.append("      SEASON_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{seriesGuid}, new SeasonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

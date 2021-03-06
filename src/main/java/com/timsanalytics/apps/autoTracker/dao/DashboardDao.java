package com.timsanalytics.apps.autoTracker.dao;

import com.timsanalytics.common.beans.KeyValueDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public DashboardDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public Double getLongestTimeBetweenFills() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FILL_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, (rs, rowNum) -> rs.getDouble("MAX_DISTANCE"));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Double getLongestDistanceBetweenFills() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FILL_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, (rs, rowNum) -> rs.getDouble("MAX_DISTANCE"));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<KeyValueDouble> getOdometerData() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_ODOMETER\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_ODOMETER\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE_TIME"), rs.getDouble("FILL_ODOMETER")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<KeyValueDouble> getMpgData() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_MILES_TRAVELED / FILL_GALLONS AS MPG\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_DATE_TIME;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE_TIME"), rs.getDouble("MPG")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

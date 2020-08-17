package com.timsanalytics.main.thisApp.dao;

import com.timsanalytics.common.beans.KeyValueDouble;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTrackerDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public FuelTrackerDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public Double getLongestDistance() {
        this.logger.trace("FuelTrackerDao -> getLongestDistance");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FILL_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      FUEL_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      FILL_STATUS = 'Active'\n");
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
        this.logger.trace("FuelTrackerDao -> getOdometerData");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE,\n");
        query.append("      FILL_ODOMETER\n");
        query.append("  FROM\n");
        query.append("      FUEL_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      FILL_STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_ODOMETER\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE"), rs.getDouble("FILL_ODOMETER")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<KeyValueDouble> getMpgData() {
        this.logger.trace("FuelTrackerDao -> getMpgData");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE,\n");
        query.append("      FILL_MILES_TRAVELED / FILL_GALLONS AS MPG\n");
        query.append("  FROM\n");
        query.append("      FUEL_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      FILL_STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_DATE;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE"), rs.getDouble("MPG")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

}

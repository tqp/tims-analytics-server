package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.KeyValueLong;
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

    public List<KeyValueLong> getFuelInfo() {
        this.logger.trace("FuelTrackerDao -> getFuelInfo");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE,\n");
        query.append("      FILL_GALLONS\n");
        query.append("  FROM\n");
        query.append("      FUEL_TRACKER.FILL\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_DATE;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueLong(rs.getString("FILL_DATE"), rs.getLong("FILL_GALLONS")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

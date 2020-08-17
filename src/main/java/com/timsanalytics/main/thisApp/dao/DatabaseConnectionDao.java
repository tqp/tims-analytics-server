package com.timsanalytics.main.thisApp.dao;

import com.timsanalytics.common.beans.KeyValueString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectionDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public DatabaseConnectionDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public KeyValueString getDatabaseResponse() {
        this.logger.trace("DatabaseConnectionDao -> getDatabaseResponse");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      NOW() AS TIMESTAMP\n");
        query.append("  FROM \n");
        query.append("      DUAL\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueString("response", rs.getString("TIMESTAMP")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.KeyValueString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseConnectionDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public DatabaseConnectionDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public List<KeyValueString> getDatabaseResponse() {
        this.logger.trace("DatabaseConnectionDao -> getDatabaseResponse");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      NOW() AS TIMESTAMP\n");
        query.append("  FROM \n");
        query.append("      DUAL\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> {
                return new KeyValueString("response", rs.getString("TIMESTAMP"));
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

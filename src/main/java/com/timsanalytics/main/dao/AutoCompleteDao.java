package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCompleteDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public AutoCompleteDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public List<Person> getAutoCompleteLastName(String filter) {
        this.logger.trace("AutoCompleteDao -> GetAutoCompleteLastName");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON_LAST_NAME,\n");
        query.append("      PERSON_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      LOWER(PERSON_LAST_NAME) LIKE LOWER(?)\n");
        query.append("  GROUP BY\n");
        query.append("      PERSON_LAST_NAME,\n");
        query.append("      PERSON_FIRST_NAME\n");
        query.append("  ORDER BY\n");
        query.append("      PERSON_LAST_NAME,\n");
        query.append("      PERSON_FIRST_NAME\n");
        query.append("  LIMIT 0, 5\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("Filter:\n" + filter);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{filter + "%"},
                    (rs, rowNum) -> {
                        Person person = new Person();
                        person.setLastName(rs.getString("PERSON_LAST_NAME"));
                        person.setFirstName(rs.getString("PERSON_FIRST_NAME"));
                        return person;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Person> getAutoCompleteAddress(String filter) {
        this.logger.trace("AutoCompleteDao -> GetAutoCompleteAddress");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON_STREET,\n");
        query.append("      PERSON_CITY,\n");
        query.append("      PERSON_STATE,\n");
        query.append("      PERSON_ZIP_CODE\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      LOWER(PERSON_STREET) LIKE LOWER(?)\n");
        query.append("  GROUP BY\n");
        query.append("      PERSON_STREET,\n");
        query.append("      PERSON_CITY,\n");
        query.append("      PERSON_STATE,\n");
        query.append("      PERSON_ZIP_CODE\n");
        query.append("  ORDER BY\n");
        query.append("      PERSON_STREET,\n");
        query.append("      PERSON_CITY,\n");
        query.append("      PERSON_STATE,\n");
        query.append("      PERSON_ZIP_CODE\n");
        query.append("  LIMIT 0, 5\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("Filter:\n" + filter);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{"%" + filter + "%"},
                    (rs, rowNum) -> {
                        Person person = new Person();
                        person.setStreet(rs.getString("PERSON_STREET"));
                        person.setCity(rs.getString("PERSON_CITY"));
                        person.setState(rs.getString("PERSON_STATE"));
                        person.setZipCode(rs.getString("PERSON_ZIP_CODE"));
                        return person;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

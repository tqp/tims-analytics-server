package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Contestant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContestantRowMapper implements RowMapper<Contestant> {

    public Contestant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contestant row = new Contestant();
        row.setGuid(rs.getString("CONTESTANT_GUID"));
        row.setLastName(rs.getString("CONTESTANT_LAST_NAME"));
        row.setFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
        row.setGender(rs.getString("CONTESTANT_GENDER"));
        return row;
    }
}

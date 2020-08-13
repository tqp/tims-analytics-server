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
        row.setDateOfBirth(rs.getString("CONTESTANT_DATE_OF_BIRTH"));
        row.setOccupation(rs.getString("CONTESTANT_OCCUPATION"));
        row.setHometownCity(rs.getString("CONTESTANT_HOMETOWN_CITY"));
        row.setHometownState(rs.getString("CONTESTANT_HOMETOWN_STATE"));
        row.setTwitterHandle(rs.getString("CONTESTANT_TWITTER_HANDLE"));
        row.setComments(rs.getString("CONTESTANT_COMMENTS"));
        row.setStatus(rs.getString("STATUS"));
        return row;
    }
}

package com.timsanalytics.main.realityTracker.dao.RowMappers;

import com.timsanalytics.main.realityTracker.beans.Contestant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContestantRowMapper implements RowMapper<Contestant> {

    public Contestant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contestant row = new Contestant();
        row.setContestantGuid(rs.getString("CONTESTANT_GUID"));
        row.setContestantLastName(rs.getString("CONTESTANT_LAST_NAME"));
        row.setContestantFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
        row.setContestantNickname(rs.getString("CONTESTANT_NICKNAME"));
        row.setContestantGender(rs.getString("CONTESTANT_GENDER"));
        row.setContestantDateOfBirth(rs.getString("CONTESTANT_DATE_OF_BIRTH"));
        row.setContestantOccupation(rs.getString("CONTESTANT_OCCUPATION"));
        row.setContestantHometownCity(rs.getString("CONTESTANT_HOMETOWN_CITY"));
        row.setContestantHometownState(rs.getString("CONTESTANT_HOMETOWN_STATE"));
        row.setContestantTwitterHandle(rs.getString("CONTESTANT_TWITTER_HANDLE"));
        row.setContestantComments(rs.getString("CONTESTANT_COMMENTS"));
        row.setStatus(rs.getString("STATUS"));
        return row;
    }
}

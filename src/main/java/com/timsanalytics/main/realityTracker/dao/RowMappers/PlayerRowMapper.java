package com.timsanalytics.main.realityTracker.dao.RowMappers;

import com.timsanalytics.main.realityTracker.beans.Player;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRowMapper implements RowMapper<Player> {

    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        Player row = new Player();
        row.setPlayerGuid(rs.getString("PLAYER_GUID"));
        row.setContestantGuid(rs.getString("CONTESTANT_GUID"));
        row.setContestantLastName(rs.getString("CONTESTANT_LAST_NAME"));
        row.setContestantFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
        return row;
    }
}

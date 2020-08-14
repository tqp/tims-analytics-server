package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonRowMapper implements RowMapper<Season> {

    public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
        Season row = new Season();
        row.setSeriesGuid(rs.getString("SERIES_GUID"));
        row.setGuid(rs.getString("SEASON_GUID"));
        row.setName(rs.getString("SEASON_NAME"));
        return row;
    }
}

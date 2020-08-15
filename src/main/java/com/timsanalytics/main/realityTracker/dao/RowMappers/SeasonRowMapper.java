package com.timsanalytics.main.realityTracker.dao.RowMappers;

import com.timsanalytics.main.realityTracker.beans.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonRowMapper implements RowMapper<Season> {

    public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
        Season row = new Season();
        row.setGuid(rs.getString("SEASON_GUID"));
        row.setName(rs.getString("SEASON_NAME"));
        row.setAbbreviation(rs.getString("SEASON_ABBREVIATION"));
        row.setSeriesGuid(rs.getString("SERIES_GUID"));
        row.setSeriesName(rs.getString("SERIES_NAME"));
        return row;
    }
}

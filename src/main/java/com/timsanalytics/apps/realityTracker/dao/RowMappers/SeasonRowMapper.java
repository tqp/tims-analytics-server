package com.timsanalytics.apps.realityTracker.dao.RowMappers;

import com.timsanalytics.apps.realityTracker.beans.Season;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonRowMapper implements RowMapper<Season> {

    public Season mapRow(ResultSet rs, int rowNum) throws SQLException {
        Season row = new Season();
        row.setSeasonGuid(rs.getString("SEASON_GUID"));
        row.setSeasonName(rs.getString("SEASON_NAME"));
        row.setSeasonAbbreviation(rs.getString("SEASON_ABBREVIATION"));
        row.setSeriesGuid(rs.getString("SERIES_GUID"));
        row.setSeriesName(rs.getString("SERIES_NAME"));
        return row;
    }
}

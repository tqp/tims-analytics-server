package com.timsanalytics.apps.realityTracker.dao.RowMappers;

import com.timsanalytics.apps.realityTracker.beans.Series;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeriesRowMapper implements RowMapper<Series> {

    public Series mapRow(ResultSet rs, int rowNum) throws SQLException {
        Series row = new Series();
        row.setSeriesGuid(rs.getString("SERIES_GUID"));
        row.setSeriesName(rs.getString("SERIES_NAME"));
        row.setSeriesAbbreviation(rs.getString("SERIES_ABBREVIATION"));
        return row;
    }
}

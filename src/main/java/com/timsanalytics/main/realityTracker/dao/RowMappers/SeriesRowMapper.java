package com.timsanalytics.main.realityTracker.dao.RowMappers;

import com.timsanalytics.main.realityTracker.beans.Series;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeriesRowMapper implements RowMapper<Series> {

    public Series mapRow(ResultSet rs, int rowNum) throws SQLException {
        Series row = new Series();
        row.setGuid(rs.getString("SERIES_GUID"));
        row.setName(rs.getString("SERIES_NAME"));
        row.setAbbreviation(rs.getString("SERIES_ABBREVIATION"));
        return row;
    }
}

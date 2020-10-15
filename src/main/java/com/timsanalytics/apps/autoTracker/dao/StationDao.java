package com.timsanalytics.apps.autoTracker.dao;

import com.timsanalytics.apps.autoTracker.beans.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public StationDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public List<Station> getAutoCompleteStationName(String filter) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      STATION_GUID,\n");
        query.append("      STATION_NAME,\n");
        query.append("      STATION_AFFILIATION,\n");
        query.append("      STATION_ADDRESS,\n");
        query.append("      STATION_CITY,\n");
        query.append("      STATION_STATE,\n");
        query.append("      STATION_ZIP,\n");
        query.append("      STATION_PHONE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.STATION\n");
        query.append("  WHERE\n");
        query.append("      LOWER(STATION_NAME) LIKE LOWER(?)\n");
        query.append("      AND STATION_STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      STATION_NAME\n");
        query.append("  LIMIT 5\n");
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{"%" + filter + "%"},
                    (rs, rowNum) -> {
                        Station fuelStation = new Station();
                        fuelStation.setStationGuid(rs.getString("STATION_GUID"));
                        fuelStation.setStationName(rs.getString("STATION_NAME"));
                        fuelStation.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                        fuelStation.setStationAddress(rs.getString("STATION_ADDRESS"));
                        fuelStation.setStationCity(rs.getString("STATION_CITY"));
                        fuelStation.setStationState(rs.getString("STATION_STATE"));
                        fuelStation.setStationZip(rs.getString("STATION_ZIP"));
                        fuelStation.setStationPhone(rs.getString("STATION_PHONE"));
                        return fuelStation;
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("FuelStationDao -> getAutoCompleteStationName -> EmptyResultDataAccessException: " + e);
            return null;
        }
    }
}

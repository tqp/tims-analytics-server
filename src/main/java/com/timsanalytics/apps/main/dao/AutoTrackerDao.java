package com.timsanalytics.apps.main.dao;

import com.timsanalytics.apps.main.beans.Fill;
import com.timsanalytics.apps.main.beans.FuelActivity;
import com.timsanalytics.apps.main.beans.Station;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.KeyValueDouble;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.utils.GenerateUuidService;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class AutoTrackerDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;
    private final PrintObjectService printObjectService;

    @Autowired
    public AutoTrackerDao(JdbcTemplate mySqlAuthJdbcTemplate,
                          GenerateUuidService generateUuidService,
                          PrintObjectService printObjectService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
        this.printObjectService = printObjectService;
    }

    // FUEL ACTIVITY

    public Fill createFuelActivity(Fill fill) {
        this.printObjectService.PrintObject("fill", fill);
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("      (\n");
        query.append("          FILL.FILL_GUID,\n");
        query.append("          FILL.FILL_ODOMETER,\n");
        query.append("          FILL.FILL_DATE_TIME,\n");
        query.append("          FILL.FILL_GALLONS,\n");
        query.append("          FILL.FILL_COST_PER_GALLON,\n");
        query.append("          FILL.FILL_TOTAL_COST,\n");
        query.append("          FILL.FILL_MILES_TRAVELED,\n");
        query.append("          FILL.FILL_MILES_PER_GALLON,\n");
        query.append("          FILL.FILL_COMMENTS,\n");
        query.append("          FILL.STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        fill.setFillGuid(this.generateUuidService.GenerateUuid());
                        this.logger.debug("New Fill GUID: " + fill.getFillGuid());
                        ps.setString(1, fill.getFillGuid());
                        ps.setDouble(2, fill.getFillOdometer());
                        ps.setTimestamp(3, fill.getFillDateTime());
                        ps.setDouble(4, fill.getFillGallons());
                        ps.setDouble(5, fill.getFillCostPerGallon());
                        ps.setDouble(6, fill.getFillTotalCost());
                        ps.setDouble(7, fill.getFillMilesTraveled());
                        ps.setDouble(8, fill.getFillMilesPerGallon());
                        ps.setString(9, fill.getFillComments());
                        return ps;
                    }
            );
            return fill;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public int getFuelActivityList_SSP_TotalRecords(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder query = new StringBuilder();
        query.append("          SELECT\n");
        query.append("              COUNT(*)\n");
        query.append("          FROM\n");
        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getFuelActivityList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");
        try {
            Integer count = this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, Integer.class);
            return count == null ? 0 : count;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return 0;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return 0;
        }
    }

    public List<FuelActivity> getFuelActivityList() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_GUID,\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_ODOMETER,\n");
        query.append("      FILL_MILES_TRAVELED,\n");
        query.append("      FILL_MILES_PER_GALLON,\n");
        query.append("      FILL_GALLONS,\n");
        query.append("      FILL_COST_PER_GALLON,\n");
        query.append("      FILL_TOTAL_COST,\n");
        query.append("      FILL_COMMENTS,\n");
        query.append("      FILL.STATION_GUID AS FILL_STATION_GUID,\n");
        query.append("      STATION.STATION_GUID AS STATION_STATION_GUID,\n");
        query.append("      STATION.STATION_NAME,\n");
        query.append("      STATION.STATION_AFFILIATION,\n");
        query.append("      STATION.STATION_CITY,\n");
        query.append("      STATION.STATION_STATE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("      LEFT JOIN AUTO_TRACKER.STATION ON FILL.STATION_GUID = STATION.STATION_GUID\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{},
                    (rs, rowNum) -> {
                        Fill fill = new Fill();
                        fill.setFillGuid(rs.getString("FILL_GUID"));
                        fill.setFillDateTime(rs.getTimestamp("FILL_DATE_TIME"));
                        fill.setFillOdometer(rs.getDouble("FILL_ODOMETER"));
                        fill.setFillMilesTraveled(rs.getDouble("FILL_MILES_TRAVELED"));
                        fill.setFillMilesPerGallon(rs.getDouble("FILL_MILES_PER_GALLON"));
                        fill.setFillGallons(rs.getDouble("FILL_GALLONS"));
                        fill.setFillCostPerGallon(rs.getDouble("FILL_COST_PER_GALLON"));
                        fill.setFillTotalCost(rs.getDouble("FILL_TOTAL_COST"));
                        fill.setFillComments(rs.getString("FILL_COMMENTS"));
                        fill.setStationGuid(rs.getString("FILL_STATION_GUID"));

                        Station station = new Station();
                        station.setStationGuid(rs.getString("STATION_STATION_GUID"));
                        station.setStationName(rs.getString("STATION_NAME"));
                        station.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                        station.setStationCity(rs.getString("STATION_CITY"));
                        station.setStationState(rs.getString("STATION_STATE"));

                        return new FuelActivity(fill, station);
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<FuelActivity> getFuelActivityList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        int pageStart = (serverSidePaginationRequest.getPageIndex()) * serverSidePaginationRequest.getPageSize();
        int pageSize = serverSidePaginationRequest.getPageSize();

        String sortColumn = serverSidePaginationRequest.getSortColumn();
        String sortDirection = serverSidePaginationRequest.getSortDirection();

        StringBuilder query = new StringBuilder();
        query.append("  -- PAGINATION QUERY\n");
        query.append("  SELECT\n");
        query.append("      FILTER_SORT_QUERY.*\n");
        query.append("  FROM\n");

        query.append("      -- FILTER/SORT QUERY\n");
        query.append("      (\n");
        query.append("          SELECT\n");
        query.append("              *\n");
        query.append("          FROM\n");

        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getFuelActivityList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append(sortColumn).append(" ").append(sortDirection.toUpperCase()).append(",\n");
        query.append("              FILL_DATE_TIME DESC\n"); // UPDATE THIS WHEN USING AS TEMPLATE!
        query.append("      ) AS FILTER_SORT_QUERY\n");
        query.append("      -- END FILTER/SORT QUERY\n");

        query.append("  LIMIT ?, ?\n");
        query.append("  -- END PAGINATION QUERY\n");

        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("pageStart=" + pageStart + ", pageSize=" + pageSize);

        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{
                    pageStart,
                    pageSize
            }, (rs, rowNum) -> {
                Fill fill = new Fill();
                fill.setFillGuid(rs.getString("FILL_GUID"));
                fill.setFillDateTime(rs.getTimestamp("FILL_DATE_TIME"));
                fill.setFillOdometer(rs.getDouble("FILL_ODOMETER"));
                fill.setFillMilesTraveled(rs.getDouble("FILL_MILES_TRAVELED"));
                fill.setFillMilesPerGallon(rs.getDouble("FILL_MILES_PER_GALLON"));
                fill.setFillGallons(rs.getDouble("FILL_GALLONS"));
                fill.setFillCostPerGallon(rs.getDouble("FILL_COST_PER_GALLON"));
                fill.setFillTotalCost(rs.getDouble("FILL_TOTAL_COST"));
                fill.setFillComments(rs.getString("FILL_COMMENTS"));
                fill.setStationGuid(rs.getString("FILL_STATION_GUID"));

                Station station = new Station();
                station.setStationGuid(rs.getString("STATION_STATION_GUID"));
                station.setStationName(rs.getString("STATION_NAME"));
                station.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                station.setStationCity(rs.getString("STATION_CITY"));
                station.setStationState(rs.getString("STATION_STATE"));

                return new FuelActivity(fill, station);
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    private String getFuelActivityList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder rootQuery = new StringBuilder();
        rootQuery.append("              SELECT\n");
        rootQuery.append("                  FILL_GUID,\n");
        rootQuery.append("                  FILL_DATE_TIME,\n");
        rootQuery.append("                  FILL_ODOMETER,\n");
        rootQuery.append("                  FILL_MILES_TRAVELED,\n");
        rootQuery.append("                  FILL_MILES_PER_GALLON,\n");
        rootQuery.append("                  FILL_GALLONS,\n");
        rootQuery.append("                  FILL_COST_PER_GALLON,\n");
        rootQuery.append("                  FILL_TOTAL_COST,\n");
        rootQuery.append("                  FILL_COMMENTS,\n");
        rootQuery.append("                  FILL.STATION_GUID AS FILL_STATION_GUID,\n");
        rootQuery.append("                  STATION.STATION_GUID AS STATION_STATION_GUID,\n");
        rootQuery.append("                  STATION.STATION_NAME,\n");
        rootQuery.append("                  STATION.STATION_AFFILIATION,\n");
        rootQuery.append("                  STATION.STATION_CITY,\n");
        rootQuery.append("                  STATION.STATION_STATE\n");
        rootQuery.append("              FROM\n");
        rootQuery.append("                  AUTO_TRACKER.FILL\n");
        rootQuery.append("                  LEFT JOIN AUTO_TRACKER.STATION ON FILL.STATION_GUID = STATION.STATION_GUID\n");
        rootQuery.append("              WHERE\n");
        rootQuery.append("              (\n");
        rootQuery.append("                  FILL.STATUS = 'Active'\n");
        rootQuery.append("                  AND\n");
        rootQuery.append(getFuelActivityList_SSP_AdditionalWhereClause(serverSidePaginationRequest));
        rootQuery.append("              )");
        return rootQuery.toString();
    }

    private String getFuelActivityList_SSP_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder whereClause = new StringBuilder();
        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        // NAME FILTER CLAUSE
        if (!"".equalsIgnoreCase(nameFilter)) {
            whereClause.append("                  (2=2)");
//            whereClause.append("                  (\n");
//            whereClause.append("                    UPPER(FUEL_ACTIVITY.CONTESTANT_LAST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
//            whereClause.append("                    OR");
//            whereClause.append("                    UPPER(FUEL_ACTIVITY.CONTESTANT_FIRST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
//            whereClause.append("                  )\n");
        } else {
            whereClause.append("                  (1=1)");
        }

        return whereClause.toString();
    }

    public FuelActivity getFuelActivityDetail(String fuelActivityGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_GUID,\n");
        query.append("      FILL_ODOMETER,\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_GALLONS,\n");
        query.append("      FILL_COST_PER_GALLON,\n");
        query.append("      FILL_TOTAL_COST,\n");
        query.append("      FILL_MILES_TRAVELED,\n");
        query.append("      FILL_MILES_PER_GALLON,\n");
        query.append("      FILL_COMMENTS,\n");
        query.append("      FILL.STATION_GUID AS FILL_STATION_GUID,\n");
        query.append("      STATION.STATION_GUID AS STATION_STATION_GUID,\n");
        query.append("      STATION.STATION_NAME,\n");
        query.append("      STATION.STATION_AFFILIATION,\n");
        query.append("      STATION.STATION_ADDRESS,\n");
        query.append("      STATION.STATION_CITY,\n");
        query.append("      STATION.STATION_STATE,\n");
        query.append("      STATION.STATION_ZIP,\n");
        query.append("      STATION.STATION_PHONE,\n");
        query.append("      STATUS\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("      LEFT JOIN AUTO_TRACKER.STATION ON FILL.STATION_GUID = STATION.STATION_GUID\n");
        query.append("  WHERE\n");
        query.append("      FILL_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{fuelActivityGuid}, (rs, rowNum) -> {
                Fill fill = new Fill();
                fill.setFillGuid(rs.getString("FILL_GUID"));
                fill.setFillDateTime(rs.getTimestamp("FILL_DATE_TIME"));
                fill.setFillOdometer(rs.getDouble("FILL_ODOMETER"));
                fill.setFillMilesTraveled(rs.getDouble("FILL_MILES_TRAVELED"));
                fill.setFillMilesPerGallon(rs.getDouble("FILL_MILES_PER_GALLON"));
                fill.setFillGallons(rs.getDouble("FILL_GALLONS"));
                fill.setFillCostPerGallon(rs.getDouble("FILL_COST_PER_GALLON"));
                fill.setFillTotalCost(rs.getDouble("FILL_TOTAL_COST"));
                fill.setFillComments(rs.getString("FILL_COMMENTS"));
                fill.setStationGuid(rs.getString("FILL_STATION_GUID"));

                Station station = new Station();
                station.setStationGuid(rs.getString("STATION_STATION_GUID"));
                station.setStationName(rs.getString("STATION_NAME"));
                station.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                station.setStationAddress(rs.getString("STATION_ADDRESS"));
                station.setStationCity(rs.getString("STATION_CITY"));
                station.setStationState(rs.getString("STATION_STATE"));
                station.setStationZip(rs.getString("STATION_ZIP"));
                station.setStationPhone(rs.getString("STATION_PHONE"));

                return new FuelActivity(fill, station);
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Fill updateFuelActivity(Fill fill) {
        this.printObjectService.PrintObject("Temp", fill);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  SET\n");
        query.append("      FILL.FILL_ODOMETER = ?,\n");
        query.append("      FILL.FILL_DATE_TIME = ?,\n");
        query.append("      FILL.FILL_GALLONS = ?,\n");
        query.append("      FILL.FILL_COST_PER_GALLON = ?,\n");
        query.append("      FILL.FILL_TOTAL_COST = ?,\n");
        query.append("      FILL.FILL_MILES_TRAVELED = ?,\n");
        query.append("      FILL.FILL_MILES_PER_GALLON = ?,\n");
        query.append("      FILL.FILL_COMMENTS = ?,\n");
        query.append("      FILL.STATION_GUID = ?\n");
        query.append("  WHERE\n");
        query.append("      FILL.FILL_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setDouble(1, fill.getFillOdometer());
                        ps.setTimestamp(2, fill.getFillDateTime());
                        ps.setDouble(3, fill.getFillGallons());
                        ps.setDouble(4, fill.getFillCostPerGallon());
                        ps.setDouble(5, fill.getFillTotalCost());
                        ps.setDouble(6, fill.getFillMilesTraveled());
                        ps.setDouble(7, fill.getFillMilesPerGallon());
                        ps.setString(8, fill.getFillComments());
                        ps.setString(9, fill.getStationGuid());
                        ps.setString(10, fill.getFillGuid());
                        return ps;
                    }
            );
            return fill;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public KeyValue deleteFuelActivity(String fuelActivityGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      FILL_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("FILL_GUID=" + fuelActivityGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, fuelActivityGuid);
                        return ps;
                    }
            );
            return new KeyValue("fuelActivityGuid", fuelActivityGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // STATION

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

    // DASHBOARD

    public Double getLongestTimeBetweenFills() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FILL_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, (rs, rowNum) -> rs.getDouble("MAX_DISTANCE"));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Double getLongestDistanceBetweenFills() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FILL_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, (rs, rowNum) -> rs.getDouble("MAX_DISTANCE"));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<KeyValueDouble> getOdometerData() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_ODOMETER\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_ODOMETER\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE_TIME"), rs.getDouble("FILL_ODOMETER")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<KeyValueDouble> getMpgData() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      FILL_DATE_TIME,\n");
        query.append("      FILL_MILES_TRAVELED / FILL_GALLONS AS MPG\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FILL\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FILL_DATE_TIME;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FILL_DATE_TIME"), rs.getDouble("MPG")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

}

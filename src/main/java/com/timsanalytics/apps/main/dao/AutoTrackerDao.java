package com.timsanalytics.apps.main.dao;

import com.timsanalytics.apps.main.beans.FuelActivity;
import com.timsanalytics.common.beans.KeyValueDouble;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoTrackerDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public AutoTrackerDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    // Fuel Activity

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
        query.append("              FUEL_ACTIVITY_DATE DESC\n");;
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
                FuelActivity item = new FuelActivity();
                item.setFuelActivityGuid(rs.getString("FUEL_ACTIVITY_GUID"));
                item.setFuelActivityDate(rs.getDate("FUEL_ACTIVITY_DATE"));
                item.setFuelActivityOdometer(rs.getLong("FUEL_ACTIVITY_ODOMETER"));
                item.setStationGuid(rs.getString("STATION_GUID"));
                item.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                item.setStationCity(rs.getString("STATION_CITY"));
                item.setStationState(rs.getString("STATION_STATE"));
                return item;
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

        rootQuery.append("              SELECT");
        rootQuery.append("                  FUEL_ACTIVITY.FUEL_ACTIVITY_GUID,");
        rootQuery.append("                  FUEL_ACTIVITY.FUEL_ACTIVITY_DATE,");
        rootQuery.append("                  FUEL_ACTIVITY.FUEL_ACTIVITY_ODOMETER,");
        rootQuery.append("                  FUEL_ACTIVITY.STATION_GUID,");
        rootQuery.append("                  STATION.STATION_AFFILIATION,");
        rootQuery.append("                  STATION.STATION_CITY,");
        rootQuery.append("                  STATION.STATION_STATE");
        rootQuery.append("              FROM");
        rootQuery.append("                  AUTO_TRACKER.FUEL_ACTIVITY");
        rootQuery.append("                  LEFT JOIN AUTO_TRACKER.STATION ON FUEL_ACTIVITY.STATION_GUID = STATION.STATION_GUID");
        rootQuery.append("              WHERE");
        rootQuery.append("              (");
        rootQuery.append("                  FUEL_ACTIVITY.STATUS = 'Active'");
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
        query.append("      FUEL_ACTIVITY_GUID,\n");
        query.append("      FUEL_ACTIVITY_ODOMETER,\n");
        query.append("      FUEL_ACTIVITY_DATE,\n");
        query.append("      FUEL_ACTIVITY_GALLONS,\n");
        query.append("      FUEL_ACTIVITY_COST_PER_GALLON,\n");
        query.append("      FUEL_ACTIVITY_TOTAL_COST,\n");
        query.append("      FUEL_ACTIVITY_MILES_TRAVELED,\n");
        query.append("      FUEL_ACTIVITY_MILES_PER_GALLON,\n");
        query.append("      FUEL_ACTIVITY_COMMENTS,\n");
        query.append("      FUEL_ACTIVITY.STATION_GUID,\n");
        query.append("      STATION.STATION_NAME,\n");
        query.append("      STATION.STATION_AFFILIATION,\n");
        query.append("      STATION.STATION_ADDRESS1,\n");
        query.append("      STATION.STATION_CITY,\n");
        query.append("      STATION.STATION_STATE,\n");
        query.append("      STATION.STATION_ZIP,\n");
        query.append("      STATION.STATION_PHONE,\n");
        query.append("      STATUS\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FUEL_ACTIVITY\n");
        query.append("      LEFT JOIN AUTO_TRACKER.STATION ON FUEL_ACTIVITY.STATION_GUID = STATION.STATION_GUID\n");
        query.append("  WHERE\n");
        query.append("      FUEL_ACTIVITY_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{fuelActivityGuid}, (rs, rowNum) -> {
                FuelActivity item = new FuelActivity();
                item.setFuelActivityGuid(rs.getString("FUEL_ACTIVITY_GUID"));
                item.setFuelActivityOdometer(rs.getLong("FUEL_ACTIVITY_ODOMETER"));
                item.setFuelActivityDate(rs.getDate("FUEL_ACTIVITY_DATE"));
                item.setFuelActivityGallons(rs.getDouble("FUEL_ACTIVITY_GALLONS"));
                item.setFuelActivityCostPerGallon(rs.getDouble("FUEL_ACTIVITY_COST_PER_GALLON"));
                item.setFuelActivityTotalCost(rs.getDouble("FUEL_ACTIVITY_TOTAL_COST"));
                item.setFuelActivityMilesTraveled(rs.getDouble("FUEL_ACTIVITY_MILES_TRAVELED"));
                item.setFuelActivityMilesPerGallon(rs.getDouble("FUEL_ACTIVITY_MILES_PER_GALLON"));
                item.setFuelActivityComments(rs.getString("FUEL_ACTIVITY_COMMENTS"));
                item.setStationGuid(rs.getString("STATION_GUID"));
                item.setStationName(rs.getString("STATION_NAME"));
                item.setStationAffiliation(rs.getString("STATION_AFFILIATION"));
                item.setStationAddress(rs.getString("STATION_ADDRESS1"));
                item.setStationCity(rs.getString("STATION_CITY"));
                item.setStationState(rs.getString("STATION_STATE"));
                item.setStationZip(rs.getString("STATION_ZIP"));
                item.setStationPhone(rs.getString("STATION_PHONE"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // Dashboard

    public Double getLongestTimeBetweenFills() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      MAX(FUEL_ACTIVITY_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FUEL_ACTIVITY\n");
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
        query.append("      MAX(FUEL_ACTIVITY_MILES_TRAVELED) AS MAX_DISTANCE\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FUEL_ACTIVITY\n");
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
        query.append("      FUEL_ACTIVITY_DATE,\n");
        query.append("      FUEL_ACTIVITY_ODOMETER\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FUEL_ACTIVITY\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FUEL_ACTIVITY_ODOMETER\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FUEL_ACTIVITY_DATE"), rs.getDouble("FUEL_ACTIVITY_ODOMETER")));
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
        query.append("      FUEL_ACTIVITY_DATE,\n");
        query.append("      FUEL_ACTIVITY_MILES_TRAVELED / FUEL_ACTIVITY_GALLONS AS MPG\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.FUEL_ACTIVITY\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        query.append("  ORDER BY\n");
        query.append("      FUEL_ACTIVITY_DATE;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, (rs, rowNum) -> new KeyValueDouble(rs.getString("FUEL_ACTIVITY_DATE"), rs.getDouble("MPG")));
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

}

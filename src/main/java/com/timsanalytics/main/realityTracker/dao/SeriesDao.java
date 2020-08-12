package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Series;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public SeriesDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public int getSeriesList_SSP_TotalRecords(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder query = new StringBuilder();
        query.append("          SELECT\n");
        query.append("              COUNT(*)\n");
        query.append("          FROM\n");
        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getSeriesList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");
        try {
            Integer count = this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, Integer.class);
            return count == null ? 0 : count;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("getSeriesList_SSP_TotalRecords -> EmptyResultDataAccessException: " + e);
            return 0;
        } catch (Exception e) {
            this.logger.error("getSeriesList_SSP_TotalRecords -> Exception: " + e);
            return 0;
        }
    }

    public List<Series> getSeriesList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
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
        query.append(getSeriesList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append(sortColumn).append(" ").append(sortDirection.toUpperCase()).append(",\n");
        query.append("              SERIES_NAME\n");
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
                Series item = new Series();
                item.setSeriesGuid(rs.getString("SERIES_GUID"));
                item.setSeriesName(rs.getString("SERIES_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("getSeriesList_SSP -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("getSeriesList_SSP -> Exception: " + e);
            return null;
        }
    }

    private String getSeriesList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder rootQuery = new StringBuilder();

        rootQuery.append("              SELECT");
        rootQuery.append("                  SERIES.SERIES_GUID,");
        rootQuery.append("                  SERIES.SERIES_NAME,");
        rootQuery.append("                  SERIES.STATUS,");
        rootQuery.append("                  SERIES.CREATED_ON,");
        rootQuery.append("                  SERIES.CREATED_BY,");
        rootQuery.append("                  SERIES.UPDATED_ON,");
        rootQuery.append("                  SERIES.UPDATED_BY");
        rootQuery.append("              FROM");
        rootQuery.append("                  REALITY_TRACKER.SERIES");
        rootQuery.append("              WHERE");
        rootQuery.append("              (");
        rootQuery.append("                  SERIES.STATUS = 'Active'");
        rootQuery.append("                  AND\n");
        rootQuery.append(getSeriesList_SSP_AdditionalWhereClause(serverSidePaginationRequest));
        rootQuery.append("              )");
        return rootQuery.toString();
    }

    private String getSeriesList_SSP_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder whereClause = new StringBuilder();
        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        // NAME FILTER CLAUSE
        if (!"".equalsIgnoreCase(nameFilter)) {
            whereClause.append("                  (\n");
            whereClause.append("                    UPPER(SERIES.SERIES_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
            whereClause.append("                  )\n");
        } else {
            whereClause.append("                  (1=1)");
        }

        return whereClause.toString();
    }
}

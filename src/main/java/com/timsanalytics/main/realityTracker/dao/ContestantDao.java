package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestantDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public ContestantDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public int getContestantList_SSP_TotalRecords(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder query = new StringBuilder();
        query.append("          SELECT\n");
        query.append("              COUNT(*)\n");
        query.append("          FROM\n");
        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getContestantList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");
        try {
            Integer count = this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, Integer.class);
            return count == null ? 0 : count;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("getContestantList_SSP_TotalRecords -> EmptyResultDataAccessException: " + e);
            return 0;
        } catch (Exception e) {
            this.logger.error("getContestantList_SSP_TotalRecords -> Exception: " + e);
            return 0;
        }
    }

    public List<Contestant> getContestantList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
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
        query.append(getContestantList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append(sortColumn).append(" ").append(sortDirection.toUpperCase()).append(",\n");
        query.append("              CONTESTANT_LAST_NAME,\n");
        query.append("              CONTESTANT_FIRST_NAME\n");
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
                Contestant item = new Contestant();
                item.setContestantGuid(rs.getString("CONTESTANT_GUID"));
                item.setContestantLastName(rs.getString("CONTESTANT_LAST_NAME"));
                item.setContestantFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("getContestantList_SSP -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("getContestantList_SSP -> Exception: " + e);
            return null;
        }
    }

    private String getContestantList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder rootQuery = new StringBuilder();

        rootQuery.append("              SELECT");
        rootQuery.append("                  CONTESTANT.CONTESTANT_GUID,");
        rootQuery.append("                  CONTESTANT.CONTESTANT_LAST_NAME,");
        rootQuery.append("                  CONTESTANT.CONTESTANT_FIRST_NAME,");
        rootQuery.append("                  CONTESTANT.STATUS,");
        rootQuery.append("                  CONTESTANT.CREATED_ON,");
        rootQuery.append("                  CONTESTANT.CREATED_BY,");
        rootQuery.append("                  CONTESTANT.UPDATED_ON,");
        rootQuery.append("                  CONTESTANT.UPDATED_BY");
        rootQuery.append("              FROM");
        rootQuery.append("                  REALITY_TRACKER.CONTESTANT");
        rootQuery.append("              WHERE");
        rootQuery.append("              (");
        rootQuery.append("                  CONTESTANT.STATUS = 'Active'");
        rootQuery.append("                  AND\n");
        rootQuery.append(getContestantList_SSP_AdditionalWhereClause(serverSidePaginationRequest));
        rootQuery.append("              )");
        return rootQuery.toString();
    }

    private String getContestantList_SSP_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder whereClause = new StringBuilder();
        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        // NAME FILTER CLAUSE
        if (!"".equalsIgnoreCase(nameFilter)) {
            whereClause.append("                  (\n");
            whereClause.append("                    UPPER(CONTESTANT.CONTESTANT_LAST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
            whereClause.append("                    OR");
            whereClause.append("                    UPPER(CONTESTANT.CONTESTANT_FIRST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
            whereClause.append("                  )\n");
        } else {
            whereClause.append("                  (1=1)");
        }

        return whereClause.toString();
    }
}


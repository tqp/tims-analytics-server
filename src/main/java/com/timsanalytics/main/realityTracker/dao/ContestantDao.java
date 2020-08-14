package com.timsanalytics.main.realityTracker.dao;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.realityTracker.beans.Season;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import com.timsanalytics.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class ContestantDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public ContestantDao(JdbcTemplate mySqlAuthJdbcTemplate, GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }

    public Contestant createContestant(Contestant contestant) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      REALITY_TRACKER.CONTESTANT\n");
        query.append("      (\n");
        query.append("          CONTESTANT.CONTESTANT_GUID,\n");
        query.append("          CONTESTANT.CONTESTANT_LAST_NAME,\n");
        query.append("          CONTESTANT.CONTESTANT_FIRST_NAME,\n");
        query.append("          CONTESTANT.CONTESTANT_NICKNAME,\n");
        query.append("          CONTESTANT.CONTESTANT_GENDER,\n");
        query.append("          CONTESTANT.CONTESTANT_DATE_OF_BIRTH,\n");
        query.append("          CONTESTANT.CONTESTANT_OCCUPATION,\n");
        query.append("          CONTESTANT.CONTESTANT_HOMETOWN_CITY,\n");
        query.append("          CONTESTANT.CONTESTANT_HOMETOWN_STATE,\n");
        query.append("          CONTESTANT.CONTESTANT_TWITTER_HANDLE,\n");
        query.append("          CONTESTANT.CONTESTANT_COMMENTS,\n");
        query.append("          CONTESTANT.STATUS\n");
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
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        contestant.setGuid(this.generateUuidService.GenerateUuid());
                        this.logger.trace("New Contestant GUID: " + contestant.getGuid());
                        ps.setString(1, contestant.getGuid());
                        ps.setString(2, contestant.getLastName());
                        ps.setString(3, contestant.getFirstName());
                        ps.setString(4, contestant.getNickname());
                        ps.setString(5, contestant.getGender());
                        ps.setString(6, contestant.getDateOfBirth());
                        ps.setString(7, contestant.getOccupation());
                        ps.setString(8, contestant.getHometownCity());
                        ps.setString(9, contestant.getHometownState());
                        ps.setString(10, contestant.getTwitterHandle());
                        ps.setString(11, contestant.getComments());
                        return ps;
                    }
            );
            return this.getContestantDetail(contestant.getGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
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
            this.logger.error("EmptyResultDataAccessException: " + e);
            return 0;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
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
                item.setGuid(rs.getString("CONTESTANT_GUID"));
                item.setLastName(rs.getString("CONTESTANT_LAST_NAME"));
                item.setFirstName(rs.getString("CONTESTANT_FIRST_NAME"));
                item.setNickname(rs.getString("CONTESTANT_NICKNAME"));
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

    private String getContestantList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder rootQuery = new StringBuilder();

        rootQuery.append("              SELECT");
        rootQuery.append("                  CONTESTANT.CONTESTANT_GUID,");
        rootQuery.append("                  CONTESTANT.CONTESTANT_LAST_NAME,");
        rootQuery.append("                  CONTESTANT.CONTESTANT_FIRST_NAME,");
        rootQuery.append("                  CONTESTANT.CONTESTANT_NICKNAME,");
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

    public Contestant getContestantDetail(String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      CONTESTANT_GUID,\n");
        query.append("      CONTESTANT_LAST_NAME,\n");
        query.append("      CONTESTANT_FIRST_NAME,\n");
        query.append("      CONTESTANT_NICKNAME,\n");
        query.append("      CONTESTANT_GENDER,\n");
        query.append("      CONTESTANT_DATE_OF_BIRTH,\n");
        query.append("      CONTESTANT_OCCUPATION,\n");
        query.append("      CONTESTANT_HOMETOWN_CITY,\n");
        query.append("      CONTESTANT_HOMETOWN_STATE,\n");
        query.append("      CONTESTANT_TWITTER_HANDLE,\n");
        query.append("      CONTESTANT_COMMENTS,\n");
        query.append("      STATUS\n");
        query.append("  FROM\n");
        query.append("      REALITY_TRACKER.CONTESTANT\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{contestantGuid}, new ContestantRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Contestant updateContestant(Contestant contestant) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.CONTESTANT\n");
        query.append("  SET\n");
        query.append("      CONTESTANT.CONTESTANT_LAST_NAME = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_FIRST_NAME = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_NICKNAME = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_GENDER = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_DATE_OF_BIRTH = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_OCCUPATION = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_HOMETOWN_CITY = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_HOMETOWN_STATE = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_TWITTER_HANDLE = ?,\n");
        query.append("      CONTESTANT.CONTESTANT_COMMENTS = ?\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT.CONTESTANT_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, contestant.getLastName());
                        ps.setString(2, contestant.getFirstName());
                        ps.setString(3, contestant.getNickname());
                        ps.setString(4, contestant.getGender());
                        ps.setString(5, contestant.getDateOfBirth());
                        ps.setString(6, contestant.getOccupation());
                        ps.setString(7, contestant.getHometownCity());
                        ps.setString(8, contestant.getHometownState());
                        ps.setString(9, contestant.getTwitterHandle());
                        ps.setString(10, contestant.getComments());
                        ps.setString(11, contestant.getGuid());
                        return ps;
                    }
            );
            return this.getContestantDetail(contestant.getGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public KeyValue deleteContestant(String contestantGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      REALITY_TRACKER.CONTESTANT\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      CONTESTANT_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("CONTESTANT_GUID=" + contestantGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, contestantGuid);
                        return ps;
                    }
            );
            return new KeyValue("contestantGuid", contestantGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}


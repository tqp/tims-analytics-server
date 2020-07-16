package com.timsanalytics.main.dao;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.ServerSidePaginationRequest;
import com.timsanalytics.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class PersonDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public PersonDao(JdbcTemplate mySqlAuthJdbcTemplate, GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }

    public Person createPerson(Person person) {
        this.logger.trace("PersonDao -> createPerson");
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("      (\n");
        query.append("          PERSON.PERSON_GUID,\n");
        query.append("          PERSON.PERSON_LAST_NAME,\n");
        query.append("          PERSON.PERSON_FIRST_NAME,\n");
        query.append("          PERSON.PERSON_STREET,\n");
        query.append("          PERSON.PERSON_CITY,\n");
        query.append("          PERSON.PERSON_COUNTY,\n");
        query.append("          PERSON.PERSON_STATE,\n");
        query.append("          PERSON.PERSON_ZIP_CODE,\n");
        query.append("          PERSON.PERSON_HOME_PHONE,\n");
        query.append("          PERSON.PERSON_MOBILE_PHONE,\n");
        query.append("          PERSON.PERSON_EMAIL_ADDRESS,\n");
        query.append("          PERSON.PERSON_COMPANY_NAME,\n");
        query.append("          PERSON.PERSON_COMPANY_WEB_SITE,\n");
        query.append("          PERSON.STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n"); // 1
        query.append("          ?,\n"); // 2
        query.append("          ?,\n"); // 3
        query.append("          ?,\n"); // 4
        query.append("          ?,\n"); // 5
        query.append("          ?,\n"); // 6
        query.append("          ?,\n"); // 7
        query.append("          ?,\n"); // 8
        query.append("          ?,\n"); // 9
        query.append("          ?,\n"); // 10
        query.append("          ?,\n"); // 11
        query.append("          ?,\n"); // 12
        query.append("          ?,\n");  // 13
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        person.setGuid(this.generateUuidService.GenerateUuid());
                        this.logger.debug("New Person GUID: " + person.getGuid());
                        ps.setString(1, person.getGuid());
                        ps.setString(2, person.getLastName());
                        ps.setString(3, person.getFirstName());
                        ps.setString(4, person.getStreet());
                        ps.setString(5, person.getCity());
                        ps.setString(6, person.getCounty());
                        ps.setString(7, person.getState());
                        ps.setString(8, person.getZipCode());
                        ps.setString(9, person.getHomePhone());
                        ps.setString(10, person.getMobilePhone());
                        ps.setString(11, person.getEmailAddress());
                        ps.setString(12, person.getCompanyName());
                        ps.setString(13, person.getCompanyWebsite());
                        return ps;
                    }
            );
            return this.getPersonDetail(person.getGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> createPerson -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> createPerson -> Exception: " + e);
            return null;
        }
    }

    public List<Person> getPersonList_All() {
        this.logger.trace("PersonDao -> getPersonList_All");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON.PERSON_GUID,\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME,\n");
        query.append("      PERSON.PERSON_STREET,\n");
        query.append("      PERSON.PERSON_CITY,\n");
        query.append("      PERSON.PERSON_COUNTY,\n");
        query.append("      PERSON.PERSON_STATE,\n");
        query.append("      PERSON.PERSON_ZIP_CODE,\n");
        query.append("      PERSON.PERSON_HOME_PHONE,\n");
        query.append("      PERSON.PERSON_MOBILE_PHONE,\n");
        query.append("      PERSON.PERSON_EMAIL_ADDRESS,\n");
        query.append("      PERSON.PERSON_COMPANY_NAME,\n");
        query.append("      PERSON.PERSON_COMPANY_WEB_SITE\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      PERSON.STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getPersonList_All -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getPersonList_All -> Exception: " + e);
            return null;
        }
    }

    public List<Person> getPersonList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.trace("PersonDao -> getPersonList_InfiniteScroll");

        int pageStart = (serverSidePaginationRequest.getPageIndex() - 1) * (serverSidePaginationRequest.getPageSize() + 1);
        int pageEnd = (pageStart + serverSidePaginationRequest.getPageSize() - 1);
        String filter = serverSidePaginationRequest.getFilter() != null ? serverSidePaginationRequest.getFilter() : "";

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
        query.append(getPersonList_InfiniteScroll_RootQuery());
        query.append("              WHERE\n");
        query.append("              (\n");
        query.append("                  PERSON.STATUS = 'Active'\n");
        query.append("                  AND\n");
        query.append(getPersonList_InfiniteScroll_WhereClause(filter));
        query.append("              )\n");
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append("              PERSON_LAST_NAME,\n");
        query.append("              PERSON_FIRST_NAME\n");
        query.append("      ) AS FILTER_SORT_QUERY\n");
        query.append("      -- END FILTER/SORT QUERY\n");

        query.append("  LIMIT ?, ?\n");
        query.append("  -- END PAGINATION QUERY\n");

        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("pageStart=" + pageStart + ", pageEnd=" + pageEnd);
        this.logger.trace("filter: " + filter);

        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{
                    pageStart,
                    pageEnd
            }, (rs, rowNum) -> {
                Person item = new Person();
                item.setGuid(rs.getString("PERSON_GUID"));
                item.setLastName(rs.getString("PERSON_LAST_NAME"));
                item.setFirstName(rs.getString("PERSON_FIRST_NAME"));
                item.setStreet(rs.getString("PERSON_STREET"));
                item.setCity(rs.getString("PERSON_CITY"));
                item.setCounty(rs.getString("PERSON_COUNTY"));
                item.setState(rs.getString("PERSON_STATE"));
                item.setZipCode(rs.getString("PERSON_ZIP_CODE"));
                item.setHomePhone(rs.getString("PERSON_HOME_PHONE"));
                item.setMobilePhone(rs.getString("PERSON_MOBILE_PHONE"));
                item.setEmailAddress(rs.getString("PERSON_EMAIL_ADDRESS"));
                item.setCompanyName(rs.getString("PERSON_COMPANY_NAME"));
                item.setCompanyWebsite(rs.getString("PERSON_COMPANY_WEB_SITE"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getPersonList_InfiniteScroll -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getPersonList_InfiniteScroll -> Exception: " + e);
            return null;
        }
    }

    private String getPersonList_InfiniteScroll_RootQuery() {
        return "" +
                "SELECT DISTINCT" +
                "   PERSON.PERSON_GUID,\n" +
                "   PERSON.PERSON_LAST_NAME,\n" +
                "   PERSON.PERSON_FIRST_NAME,\n" +
                "   PERSON.PERSON_STREET,\n" +
                "   PERSON.PERSON_CITY,\n" +
                "   PERSON.PERSON_COUNTY,\n" +
                "   PERSON.PERSON_STATE,\n" +
                "   PERSON.PERSON_ZIP_CODE,\n" +
                "   PERSON.PERSON_HOME_PHONE,\n" +
                "   PERSON.PERSON_MOBILE_PHONE,\n" +
                "   PERSON.PERSON_EMAIL_ADDRESS,\n" +
                "   PERSON.PERSON_COMPANY_NAME,\n" +
                "   PERSON.PERSON_COMPANY_WEB_SITE\n" +
                "FROM\n" +
                "   SAMPLE_DATA.PERSON\n";
    }

    private String getPersonList_InfiniteScroll_WhereClause(String filter) {
        this.logger.trace("PersonDao -> getPersonList_InfiniteScroll_WhereClause: filter=" + filter);
        StringBuilder whereClause = new StringBuilder();

        // IF a table-wide filter exists, search all 'searchable' fields.
        if (!"".equalsIgnoreCase(filter)) {
            whereClause.append("(\n");
            whereClause.append("    UPPER(PERSON.PERSON_LAST_NAME) LIKE UPPER('%").append(filter).append("%')\n");
            whereClause.append("    OR");
            whereClause.append("    UPPER(PERSON.PERSON_FIRST_NAME) LIKE UPPER('%").append(filter).append("%')\n");
            whereClause.append(")\n");
        } else {
            whereClause.append("(1=1)");
        }
        return whereClause.toString();
    }

    public Person getPersonDetail(String personGuid) {
        this.logger.trace("PersonDao -> getPersonDetail: personGuid=" + personGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON_GUID,\n");
        query.append("      PERSON_LAST_NAME,\n");
        query.append("      PERSON_FIRST_NAME,\n");
        query.append("      PERSON_STREET,\n");
        query.append("      PERSON_CITY,\n");
        query.append("      PERSON_STATE,\n");
        query.append("      PERSON_ZIP_CODE,\n");
        query.append("      PERSON_COUNTY,\n");
        query.append("      PERSON_HOME_PHONE,\n");
        query.append("      PERSON_MOBILE_PHONE,\n");
        query.append("      PERSON_EMAIL_ADDRESS,\n");
        query.append("      PERSON_COMPANY_NAME,\n");
        query.append("      PERSON_COMPANY_WEB_SITE\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      PERSON_GUID = ?;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{personGuid}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getPersonDetail -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getPersonDetail -> Exception: " + e);
            return null;
        }
    }

    public Person updatePerson(Person person) {
        this.logger.trace("PersonDao -> updatePerson");
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  SET\n");
        query.append("      PERSON.PERSON_LAST_NAME = ?,\n");       // 1
        query.append("      PERSON.PERSON_FIRST_NAME = ?,\n");      // 2
        query.append("      PERSON.PERSON_STREET = ?,\n");          // 3
        query.append("      PERSON.PERSON_CITY = ?,\n");            // 4
        query.append("      PERSON.PERSON_STATE = ?,\n");           // 5
        query.append("      PERSON.PERSON_ZIP_CODE = ?,\n");        // 6
        query.append("      PERSON.PERSON_COUNTY = ?,\n");          // 7
        query.append("      PERSON.PERSON_HOME_PHONE = ?,\n");      // 8
        query.append("      PERSON.PERSON_MOBILE_PHONE = ?,\n");    // 9
        query.append("      PERSON.PERSON_EMAIL_ADDRESS = ?,\n");   // 10
        query.append("      PERSON.PERSON_COMPANY_NAME = ?,\n");    // 11
        query.append("      PERSON.PERSON_COMPANY_WEB_SITE = ?\n"); // 12
        query.append("  WHERE\n");
        query.append("      PERSON.PERSON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, person.getLastName());
                        ps.setString(2, person.getFirstName());
                        ps.setString(3, person.getStreet());
                        ps.setString(4, person.getCity());
                        ps.setString(5, person.getState());
                        ps.setString(6, person.getZipCode());
                        ps.setString(7, person.getCounty());
                        ps.setString(8, person.getHomePhone());
                        ps.setString(9, person.getMobilePhone());
                        ps.setString(10, person.getEmailAddress());
                        ps.setString(11, person.getCompanyName());
                        ps.setString(12, person.getCompanyWebsite());
                        ps.setString(13, person.getGuid());
                        return ps;
                    }
            );
            return this.getPersonDetail(person.getGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> updatePerson -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> updatePerson -> Exception: " + e);
            return null;
        }
    }

    public KeyValue deletePerson(String personGuid) {
        this.logger.trace("PersonDao -> deletePerson: personGuid=" + personGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      PERSON_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("PERSON_GUID=" + personGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, personGuid);
                        return ps;
                    }
            );
            return new KeyValue("personGuid", personGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> deletePerson -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> deletePerson -> Exception: " + e);
            return null;
        }
    }

    // Sub-List

    public List<Person> getPersonSubList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.trace("PersonDao -> getPersonList_InfiniteScroll");

        int pageStart = (serverSidePaginationRequest.getPageIndex() - 1) * (serverSidePaginationRequest.getPageSize() + 1);
        int pageEnd = (pageStart + serverSidePaginationRequest.getPageSize() - 1);
        String filter = serverSidePaginationRequest.getFilter() != null ? serverSidePaginationRequest.getFilter() : "";

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
        query.append(getPersonSubList_InfiniteScroll_RootQuery());
        query.append("              WHERE\n");
        query.append("              (\n");
        query.append(getPersonSubList_InfiniteScroll_WhereClause(filter));
        query.append("              )\n");
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append("              PERSON_LAST_NAME,\n");
        query.append("              PERSON_FIRST_NAME\n");
        query.append("      ) AS FILTER_SORT_QUERY\n");
        query.append("      -- END FILTER/SORT QUERY\n");

        query.append("  LIMIT ?, ?\n");
        query.append("  -- END PAGINATION QUERY\n");

        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("pageStart: " + pageStart + " - pageEnd: " + pageEnd);
        this.logger.trace("filter: " + filter);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{
                    pageStart,
                    pageEnd
            }, (rs, rowNum) -> {
                Person item = new Person();
                item.setGuid(rs.getString("PERSON_GUID"));
                item.setFirstName(rs.getString("PERSON_FIRST_NAME"));
                item.setLastName(rs.getString("PERSON_LAST_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getPersonSubList_InfiniteScroll -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getPersonSubList_InfiniteScroll -> Exception: " + e);
            return null;
        }
    }

    private String getPersonSubList_InfiniteScroll_RootQuery() {
        return "" +
                "           SELECT\n" +
                "               PERSON_FRIENDS.PERSON_FRIENDS_GUID,\n" +
                "               PERSON_FRIENDS.PERSON_GUID,\n" +
                "               PERSON_FRIENDS.FRIEND_GUID,\n" +
                "               PERSON.PERSON_FIRST_NAME,\n" +
                "               PERSON.PERSON_LAST_NAME\n" +
                "           FROM\n" +
                "               SAMPLE_DATA.PERSON_FRIENDS\n" +
                "               LEFT JOIN SAMPLE_DATA.PERSON ON PERSON_FRIENDS.FRIEND_GUID = PERSON.PERSON_GUID\n";
    }

    private String getPersonSubList_InfiniteScroll_WhereClause(String filter) {
        this.logger.trace("PersonDao -> getPersonSubList_InfiniteScroll_WhereClause: filter=" + filter);
        StringBuilder sb_whereClause = new StringBuilder();

        // If a table-wide filter string exists, search all 'searchable' fields.
        if (!"".equalsIgnoreCase(filter)) {
            sb_whereClause.append("         (\n");
            sb_whereClause.append("             PERSON_FRIENDS.PERSON_GUID = '").append(filter).append("'\n");
            sb_whereClause.append("         )");
        } else {
            // Otherwise, return a meaningless search filter.
            sb_whereClause.append("         (1=1)");
        }
        return sb_whereClause.toString();
    }

    // FRIEND ADD/REMOVE

    public List<Person> getCurrentFriends(String personGuid) {
        this.logger.debug("getCurrentFriends: personGuid=" + personGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT DISTINCT\n");
        query.append("      PERSON.PERSON_GUID,\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON_FRIENDS\n");
        query.append("      LEFT JOIN SAMPLE_DATA.PERSON ON SAMPLE_DATA.PERSON_FRIENDS.FRIEND_GUID = SAMPLE_DATA.PERSON.PERSON_GUID\n");
        query.append("  WHERE\n");
        query.append("      PERSON_FRIENDS.PERSON_GUID = ?\n");
        query.append("      AND PERSON.STATUS NOT IN ('Deleted')\n");
        query.append("      AND PERSON_FRIENDS.STATUS NOT IN ('Deleted', 'Removed')\n");
        query.append("  ORDER BY\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{personGuid}, (rs, rowNum) -> {
                Person item = new Person();
                item.setGuid(rs.getString("PERSON_GUID"));
                item.setFirstName(rs.getString("PERSON_FIRST_NAME"));
                item.setLastName(rs.getString("PERSON_LAST_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getCurrentFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getCurrentFriends -> Exception: " + e);
            return null;
        }
    }

    public List<Person> getAvailableFriends(String personGuid) {
        this.logger.debug("getAvailableFriends: personGuid=" + personGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT DISTINCT\n");
        query.append("      PERSON_GUID,\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      PERSON.STATUS NOT IN ('Deleted')\n");
        query.append("      AND PERSON_GUID != ?\n");
        query.append("      AND PERSON_GUID NOT IN\n");
        query.append("      (\n");
        query.append("          SELECT DISTINCT\n");
        query.append("              PERSON_FRIENDS.FRIEND_GUID\n");
        query.append("          FROM\n");
        query.append("              SAMPLE_DATA.PERSON_FRIENDS\n");
        query.append("          WHERE\n");
        query.append("              PERSON_FRIENDS.PERSON_GUID = ?\n");
        query.append("              AND PERSON_FRIENDS.STATUS NOT IN ('Deleted', 'Removed')\n");
        query.append("      )\n");
        query.append("  ORDER BY\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{personGuid, personGuid}, (rs, rowNum) -> {
                Person item = new Person();
                item.setGuid(rs.getString("PERSON_GUID"));
                item.setFirstName(rs.getString("PERSON_FIRST_NAME"));
                item.setLastName(rs.getString("PERSON_LAST_NAME"));
                return item;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> getAvailableFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getAvailableFriends -> Exception: " + e);
            return null;
        }
    }

    public int[] addFriends(String personGuid, List<Person> friendList) {
        // Define SQL for batch
        StringBuilder query = new StringBuilder();
        query.append("  MERGE INTO\n");
        query.append("      SAMPLE_DATA.PERSON_FRIENDS A\n");
        query.append("  USING\n");
        query.append("  (\n");
        query.append("      SELECT\n");
        query.append("          ? AS PERSON_GUID,\n");
        query.append("          ? AS FRIEND_GUID\n");
        query.append("      FROM\n");
        query.append("          DUAL\n");
        query.append("  ) B\n");
        query.append("  ON\n");
        query.append("  (\n");
        query.append("      A.PERSON_GUID = B.PERSON_GUID\n");
        query.append("      AND A.FRIEND_GUID = B.FRIEND_GUID\n");
        query.append("  )\n");
        query.append("  WHEN MATCHED THEN\n");
        query.append("      INSERT\n");
        query.append("      (\n");
        query.append("          PERSON_FRIENDS_GUID,\n");
        query.append("          PERSON_GUID,\n");
        query.append("          FRIEND_GUID\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          B.PERSON_GUID,\n");
        query.append("          B.FRIEND_GUID,\n");
        query.append("          'Active'\n");
        query.append("      )\n");

        String uuid = this.generateUuidService.GenerateUuid();

        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, personGuid);
                            ps.setString(2, friendList.get(i).getGuid());
                            ps.setString(3, uuid);
                        }

                        @Override
                        public int getBatchSize() {
                            return friendList.size();
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> addFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> addFriends -> Exception: " + e);
            return null;
        }
    }

    public int[] removeFriends(String personGuid, List<Person> friendList) {
        // Define SQL for batch
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      SAMPLE_DATA.PERSON_FRIENDS A\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      PERSON_GUID = ?\n");
        query.append("      AND FRIEND_GUID = ?\n");

        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, personGuid);
                            ps.setString(2, friendList.get(i).getGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return friendList.size();
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("PersonDao -> addFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> addFriends -> Exception: " + e);
            return null;
        }


    }

}

package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.PersonFriend;
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
public class PersonFriendDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;

    @Autowired
    public PersonFriendDao(JdbcTemplate mySqlAuthJdbcTemplate,
                           GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }

    public List<Person> getPersonFriendList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.trace("PersonFriendDao -> getPersonFriendList_InfiniteScroll");
        int pageStart = (serverSidePaginationRequest.getPageIndex() - 1) * (serverSidePaginationRequest.getPageSize() + 1);
        int pageEnd = (pageStart + serverSidePaginationRequest.getPageSize() - 1);
        String filter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

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
        query.append(getPersonFriendList_InfiniteScroll_RootQuery(serverSidePaginationRequest));
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
            this.logger.error("PersonFriendDao -> getPersonSubList_InfiniteScroll -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonFriendDao -> getPersonSubList_InfiniteScroll -> Exception: " + e);
            return null;
        }
    }

    private String getPersonFriendList_InfiniteScroll_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder rootQuery = new StringBuilder();
        rootQuery.append("              SELECT\n");
        rootQuery.append("                  PERSON_FRIENDS.PERSON_FRIENDS_GUID,\n");
        rootQuery.append("                  PERSON_FRIENDS.PERSON_GUID,\n");
        rootQuery.append("                  PERSON_FRIENDS.FRIEND_GUID,\n");
        rootQuery.append("                  PERSON.PERSON_FIRST_NAME,\n");
        rootQuery.append("                  PERSON.PERSON_LAST_NAME\n");
        rootQuery.append("              FROM\n");
        rootQuery.append("                  SAMPLE_DATA.PERSON_FRIENDS\n");
        rootQuery.append("                  LEFT JOIN SAMPLE_DATA.PERSON ON PERSON_FRIENDS.FRIEND_GUID = PERSON.PERSON_GUID\n");
        rootQuery.append("              WHERE\n");
        rootQuery.append("              (\n");
        rootQuery.append("                  PERSON_FRIENDS.STATUS = 'Active'\n");
        rootQuery.append("                  AND\n");
        rootQuery.append(getPersonFriendList_InfiniteScroll_AdditionalWhereClause(serverSidePaginationRequest));
        rootQuery.append("              )\n");
        return rootQuery.toString();
    }

    private String getPersonFriendList_InfiniteScroll_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.trace("PersonFriendDao -> getPersonFriendList_InfiniteScroll_WhereClause");
        StringBuilder additionalWhereClause = new StringBuilder();

        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        if (!"".equalsIgnoreCase(nameFilter)) {
            additionalWhereClause.append("         (\n");
            additionalWhereClause.append("             PERSON_FRIENDS.PERSON_GUID = '").append(nameFilter).append("'\n");
            additionalWhereClause.append("         )");
        } else {
            // Otherwise, return a meaningless search filter.
            additionalWhereClause.append("         (1=1)");
        }
        return additionalWhereClause.toString();
    }

    // FRIEND ADD/REMOVE

    public PersonFriend getPersonFriendByPersonAndFriendGuid(String personGuid, String friendGuid) {
        this.logger.trace("getPersonFriendByPersonAndFriendGuid: personGuid=" + personGuid + ", friendGuid=" + friendGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON_FRIENDS_GUID,\n");
        query.append("      PERSON_GUID,\n");
        query.append("      FRIEND_GUID,\n");
        query.append("      STATUS\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON_FRIENDS\n");
        query.append("  WHERE\n");
        query.append("      PERSON_GUID = ?\n");
        query.append("      AND FRIEND_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("personGuid: " + personGuid);
        this.logger.trace("friendGuid: " + friendGuid);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{personGuid, friendGuid},
                    (rs, rowNum) -> {
                        PersonFriend item = new PersonFriend();
                        item.setPersonFriendGuid(rs.getString("PERSON_FRIENDS_GUID"));
                        item.setPersonGuid(rs.getString("PERSON_GUID"));
                        item.setFriendGuid(rs.getString("FRIEND_GUID"));
                        item.setStatus(rs.getString("STATUS"));
                        return item;
                    });
//        } catch (EmptyResultDataAccessException e) {
//            this.logger.error("PersonDao -> getPersonList_InfiniteScroll -> EmptyResultDataAccessException: " + e);
//            return null;
        } catch (Exception e) {
            this.logger.error("PersonDao -> getPersonList_InfiniteScroll -> Exception: " + e);
            return null;
        }

    }

    public List<Person> getCurrentFriends(String personGuid) {
        this.logger.trace("getCurrentFriends: personGuid=" + personGuid);
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
            this.logger.error("PersonFriendDao -> getCurrentFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonFriendDao -> getCurrentFriends -> Exception: " + e);
            return null;
        }
    }

    public List<Person> getAvailableFriends(String personGuid) {
        this.logger.trace("getAvailableFriends: personGuid=" + personGuid);
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
            this.logger.error("PersonFriendDao -> getAvailableFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonFriendDao -> getAvailableFriends -> Exception: " + e);
            return null;
        }
    }

    public int[] addFriends(List<PersonFriend> personFriendList) {
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      SAMPLE_DATA.PERSON_FRIENDS\n");
        query.append("      (\n");
        query.append("          PERSON_FRIENDS_GUID,\n");
        query.append("          PERSON_GUID,\n");
        query.append("          FRIEND_GUID,\n");
        query.append("          STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        query.append("      ON DUPLICATE KEY UPDATE\n");
        query.append("          STATUS = 'Active'\n");

        try {
            return this.mySqlAuthJdbcTemplate.batchUpdate(query.toString(),
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setString(1, personFriendList.get(i).getPersonFriendGuid());
                            ps.setString(2, personFriendList.get(i).getPersonGuid());
                            ps.setString(3, personFriendList.get(i).getFriendGuid());
                        }

                        @Override
                        public int getBatchSize() {
                            return personFriendList.size();
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
        query.append("      STATUS = 'Removed'\n");
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
            this.logger.error("PersonFriendDao -> addFriends -> EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("PersonFriendDao -> addFriends -> Exception: " + e);
            return null;
        }

    }
}

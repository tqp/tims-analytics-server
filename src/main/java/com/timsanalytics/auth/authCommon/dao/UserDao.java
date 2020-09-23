package com.timsanalytics.auth.authCommon.dao;

import com.timsanalytics.auth.authCommon.beans.Role;
import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.dao.rowMappers.RoleRowMapper;
import com.timsanalytics.auth.authCommon.dao.rowMappers.UserRowMapper;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.utils.BCryptEncoderService;
import com.timsanalytics.common.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class UserDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private GenerateUuidService generateUuidService;
    private final BCryptEncoderService bCryptEncoderService;

    @Qualifier("mySqlAuthJdbcTemplate")
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    public UserDao(GenerateUuidService generateUuidService,
                   BCryptEncoderService bCryptEncoderService,
                   JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.bCryptEncoderService = bCryptEncoderService;
    }

    public User createUser(User user, User loggedInUser) {
        this.logger.debug("UserDao -> createUser");
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      USER\n");
        query.append("      (\n");
        query.append("          USER_GUID,\n");
        query.append("          USER_USERNAME,\n");
        query.append("          USER_SURNAME,\n");
        query.append("          USER_GIVEN_NAME,\n");
        query.append("          LOGIN_COUNT,\n");
        query.append("          STATUS,\n");
        query.append("          CREATED_ON,\n");
        query.append("          CREATED_BY,\n");
        query.append("          UPDATED_ON,\n");
        query.append("          UPDATED_BY\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          0,\n");
        query.append("          'Active',\n");
        query.append("          NOW(),\n");
        query.append("          ?,\n");
        query.append("          NOW(),\n");
        query.append("          ?\n");
        query.append("      )\n");
        this.logger.debug("SQL:\n" + query.toString());

        String userGuid = this.generateUuidService.GenerateUuid();
        this.logger.debug("GUID: " + userGuid);
        this.logger.debug("Username: " + user.getUsername());
        this.logger.debug("Surname: " + user.getSurname());
        this.logger.debug("Given Name: " + user.getGivenName());
        this.logger.debug("Username: " + user.getUsername());
        this.logger.debug("Password: " + user.getPassword());
        this.logger.debug("Logged-In User: " + loggedInUser.getUsername());

        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, userGuid);
                        ps.setString(2, user.getUsername());
                        ps.setString(3, user.getSurname());
                        ps.setString(4, user.getGivenName());
                        ps.setString(5, this.bCryptEncoderService.encode(user.getPassword()));
                        ps.setString(6, user.getTheme());
                        ps.setString(7, loggedInUser.getUsername());
                        ps.setString(8, loggedInUser.getUsername());
                        return ps;
                    });
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User getUser(String userGuid) {
        this.logger.debug("UserDao -> getUser: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      USER.USER_GUID,\n");
        query.append("      USER.USER_USERNAME,\n");
        query.append("      USER.USER_SURNAME,\n");
        query.append("      USER.USER_GIVEN_NAME,\n");
        query.append("      USER.USER_PASSWORD,\n");
        query.append("      USER.USER_PROFILE_PHOTO_URL,\n");
        query.append("      USER.USER_LAST_LOGIN,\n");
        query.append("      USER.USER_LOGIN_COUNT,\n");
        query.append("      USER.STATUS,\n");
        query.append("      USER.CREATED_ON,\n");
        query.append("      USER.CREATED_BY,\n");
        query.append("      USER.UPDATED_ON,\n");
        query.append("      USER.UPDATED_BY\n");
        query.append("  FROM\n");
        query.append("      USER\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        query.append("      AND USER.STATUS = 'Active'\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{userGuid}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User getUserByRowId(String row_id) {
        this.logger.debug("UserDao -> getUserByRowId: row_id=" + row_id);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      USER.USER_GUID,\n");
        query.append("      USER.USER_USERNAME,\n");
        query.append("      USER.USER_SURNAME,\n");
        query.append("      USER.USER_GIVEN_NAME,\n");
        query.append("      USER.USER_PASSWORD,\n");
        query.append("      USER.USER_LAST_LOGIN,\n");
        query.append("      USER.USER_LOGIN_COUNT,\n");
        query.append("      USER.USER_PROFILE_PHOTO_URL,\n");
        query.append("      USER.STATUS,\n");
        query.append("      USER.CREATED_ON,\n");
        query.append("      USER.CREATED_BY,\n");
        query.append("      USER.UPDATED_ON,\n");
        query.append("      USER.UPDATED_BY\n");
        query.append("  FROM\n");
        query.append("      USER\n");
        query.append("  WHERE\n");
        query.append("      USER.ROWID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + row_id);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{row_id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public int getUserList_SSP_TotalRecords(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder query = new StringBuilder();
        query.append("          SELECT\n");
        query.append("              COUNT(*)\n");
        query.append("          FROM\n");
        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getUserList_SSP_RootQuery(serverSidePaginationRequest));
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

    public List<User> getUserList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
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
        query.append(getUserList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append(sortColumn).append(" ").append(sortDirection.toUpperCase()).append(",\n");
        query.append("              USER_SURNAME,\n");
        query.append("              USER_GIVEN_NAME\n");
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
                User row = new User();
                row.setUserGuid(rs.getString("USER_GUID"));
                row.setUsername(rs.getString("USER_USERNAME"));
                row.setSurname(rs.getString("USER_SURNAME"));
                row.setGivenName(rs.getString("USER_GIVEN_NAME"));
                return row;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    private String getUserList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder rootQuery = new StringBuilder();

        rootQuery.append("              SELECT");
        rootQuery.append("                  USER_GUID,");
        rootQuery.append("                  USER_USERNAME,");
        rootQuery.append("                  USER_LOGIN_COUNT,");
        rootQuery.append("                  USER_LAST_LOGIN,");
        rootQuery.append("                  USER_SURNAME,");
        rootQuery.append("                  USER_GIVEN_NAME");
        rootQuery.append("              FROM");
        rootQuery.append("                  AUTH.USER");
        rootQuery.append("              WHERE");
        rootQuery.append("              (");
        rootQuery.append("                  STATUS = 'Active'");
        rootQuery.append("                  AND\n");

        rootQuery.append(getUserList_SSP_AdditionalWhereClause(serverSidePaginationRequest));
        rootQuery.append("              )");
        return rootQuery.toString();
    }

    private String getUserList_SSP_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder whereClause = new StringBuilder();
        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        // NAME FILTER CLAUSE
        if (!"".equalsIgnoreCase(nameFilter)) {
            whereClause.append("                  (\n");
            whereClause.append("                    UPPER(USER.USER_SURNAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
            whereClause.append("                    OR");
            whereClause.append("                    UPPER(USER.USER_GIVEN_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
            whereClause.append("                  )\n");
        } else {
            whereClause.append("                  (1=1)");
        }

        return whereClause.toString();
    }

    public User updateUser(User User, User loggedInUser) {
        this.logger.debug("UserDao -> updateUser");
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.UPDATED_ON = NOW(),\n");
        query.append("      USER.UPDATED_BY = ?\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, loggedInUser.getUsername());
                        ps.setString(2, User.getUserGuid());
                        return ps;
                    }
            );
            return this.getUser(User.getUserGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User deleteUser(String userGuid) {
        this.logger.debug("UserDao -> deleteUser");
        StringBuilder query = new StringBuilder();
        query.append("  DELETE FROM\n");
        query.append("      USER\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, userGuid);
                        return ps;
                    }
            );
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // RELATED CRUD

    public User disableUser(String userGuid) {
        this.logger.debug("UserDao -> disableUser: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.STATUS = 'Disabled'\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, userGuid);
                        return ps;
                    }
            );
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User enableUser(String userGuid) {
        this.logger.debug("UserDao -> enableUser: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.STATUS = 'Active'\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, userGuid);
                        return ps;
                    }
            );
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User updateMyProfile(User User, User loggedInUser) {
        this.logger.debug("UserDao -> updateMyProfile: userGuid=" + loggedInUser.getUserGuid());
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.UPDATED_ON = NOW(),\n");
        query.append("      USER.UPDATED_BY = ?\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("theme: " + User.getTheme());
        this.logger.debug("userGuid: " + User.getUserGuid());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, loggedInUser.getUserGuid());
                        ps.setString(2, User.getUserGuid());
                        return ps;
                    }
            );
            return this.getUser(User.getUserGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    // NON-CRUD DAOs

    public User changePassword(User user, User loggedInUser) {
        this.logger.debug("UserDao -> changePassword: newPassword=" + user.getPassword());
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.USER_PASSWORD = ?,\n");
        query.append("      USER.UPDATED_ON = NOW(),\n");
        query.append("      USER.UPDATED_BY = ?\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + user.getUserGuid());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, this.bCryptEncoderService.encode(user.getPassword()));
                        ps.setString(2, loggedInUser.getUsername());
                        ps.setString(3, user.getUserGuid());
                        return ps;
                    }
            );
            return this.getUser(user.getUserGuid());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User updateLastLogin(String userGuid) {
        this.logger.debug("UserDao -> updateLastLogin: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.USER_LAST_LOGIN = NOW()\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, userGuid);
                        return ps;
                    }
            );
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public User incrementLoginCount(String userGuid) {
        this.logger.debug("UserDao -> incrementLoginCount: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.USER_LOGIN_COUNT = \n");
        query.append("      (\n");
        query.append("          SELECT\n");
        query.append("              USER.USER_LOGIN_COUNT\n");
        query.append("          FROM\n");
        query.append("              USER\n");
        query.append("          WHERE\n");
        query.append("              USER.USER_GUID = ?\n");
        query.append("      ) + 1\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, userGuid);
                        ps.setString(2, userGuid);
                        return ps;
                    }
            );
            return this.getUser(userGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public String getUserGuidByUsername(String username) {
        this.logger.debug("UserDao -> getUserGuidByUsername: username=" + username);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      USER.USER_GUID AS USER_GUID\n");
        query.append("  FROM\n");
        query.append("      USER\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_USERNAME = ?\n");
        query.append("      AND USER.STATUS = 'Active'\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("username: " + username);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{username}, String.class);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<Role> getUserRoles(String userGuid) {
        this.logger.debug("UserDao -> getUserRoles: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      ROLE.ROLE_GUID AS ROLE_GUID,\n");
        query.append("      ROLE.ROLE_NAME AS ROLE_NAME,\n");
        query.append("      ROLE.ROLE_AUTHORITY AS AUTHORITY\n");
        query.append("  FROM\n");
        query.append("      USER_ROLE\n");
        query.append("      LEFT JOIN ROLE ON USER_ROLE.ROLE_GUID = ROLE.ROLE_GUID\n");
        query.append("  WHERE\n");
        query.append("      USER_ROLE.USER_GUID = ?\n");
        query.append("      AND USER_ROLE.STATUS = 'Active'\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{userGuid}, new RoleRowMapper());
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

}

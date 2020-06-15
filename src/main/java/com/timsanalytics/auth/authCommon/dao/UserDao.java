package com.timsanalytics.auth.authCommon.dao;

import com.timsanalytics.auth.authCommon.beans.Role;
import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.dao.rowMappers.RoleRowMapper;
import com.timsanalytics.auth.authCommon.dao.rowMappers.UserRowMapper;
import com.timsanalytics.utils.BCryptEncoderService;
import com.timsanalytics.utils.GenerateUuidService;
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
            return null;
        }
    }

    public List<User> getUserList() {
        this.logger.debug("UserDao -> getUserList");
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
        this.logger.debug("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
            return null;
        }
    }

}

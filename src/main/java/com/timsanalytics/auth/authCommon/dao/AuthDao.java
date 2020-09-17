package com.timsanalytics.auth.authCommon.dao;

import com.timsanalytics.auth.authCommon.beans.Role;
import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authGoogle.beans.GoogleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class AuthDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Qualifier("mySqlAuthJdbcTemplate")
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    public AuthDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public User getUserForAuthentication(String username) {
        this.logger.debug("AppUserDao -> getAppUserByUsernameForAuthentication: username=" + username);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      USER.USER_GUID,\n");
        query.append("      USER.USER_USERNAME,\n");
        query.append("      USER.USER_PASSWORD,\n");
        query.append("      USER.USER_LAST_LOGIN,\n");
        query.append("      USER.USER_LOGIN_COUNT,\n");
        query.append("      USER.STATUS AS STATUS,\n");
        query.append("      USER.CREATED_ON,\n");
        query.append("      USER.CREATED_BY,\n");
        query.append("      USER.UPDATED_ON,\n");
        query.append("      USER.UPDATED_BY\n");
        query.append("  FROM\n");
        query.append("      USER\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_USERNAME = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("username: " + username);
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{username},
                    (rs, rowNum) -> {
                        User row = new User();
                        row.setUserGuid(rs.getString("USER_GUID"));
                        row.setUsername(rs.getString("USER_USERNAME"));
                        row.setPassword(rs.getString("USER_PASSWORD"));
                        row.setLastLogin(rs.getTimestamp("USER_LAST_LOGIN"));
                        row.setLoginCount(rs.getInt("USER_LOGIN_COUNT"));
                        row.setStatus(rs.getString("STATUS"));
                        row.setCreatedOn(rs.getTimestamp("CREATED_ON"));
                        row.setCreatedBy(rs.getString("CREATED_BY"));
                        row.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
                        row.setUpdatedBy(rs.getString("UPDATED_BY"));
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

    public List<Role> getRolesByUser(String userGuid) {
        this.logger.debug("AuthDao -> getRolesByUser: userGuid=" + userGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      ROLE.ROLE_GUID,\n");
        query.append("      ROLE.ROLE_NAME,\n");
        query.append("      ROLE.ROLE_AUTHORITY,\n");
        query.append("      USER_ROLE.STATUS,\n");
        query.append("      USER_ROLE.CREATED_ON,\n");
        query.append("      USER_ROLE.CREATED_BY,\n");
        query.append("      USER_ROLE.UPDATED_ON,\n");
        query.append("      USER_ROLE.UPDATED_BY\n");
        query.append("  FROM\n");
        query.append("      USER_ROLE\n");
        query.append("      LEFT JOIN ROLE ON USER_ROLE.ROLE_GUID = ROLE.ROLE_GUID\n");
        query.append("  WHERE\n");
        query.append("      USER_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + userGuid);
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{userGuid},
                    (rs, rowNum) -> {
                        Role row = new Role();
                        row.setRoleGuid(rs.getString("ROLE_GUID"));
                        row.setName(rs.getString("ROLE_NAME"));
                        row.setAuthority(rs.getString("ROLE_AUTHORITY"));
                        row.setStatus(rs.getString("STATUS"));
                        row.setCreatedOn(rs.getTimestamp("CREATED_ON"));
                        row.setCreatedBy(rs.getString("CREATED_BY"));
                        row.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
                        row.setUpdatedBy(rs.getString("UPDATED_BY"));
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

    public void updateUserRecordFromGoogleAuth(User appUser, GoogleUser googleUser) {
        this.logger.debug("AppUserDao -> updateUserRecordFromGoogleAuth: userGuid=" + appUser.getUserGuid());
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      USER\n");
        query.append("  SET\n");
        query.append("      USER.USER_PROFILE_PHOTO_URL = ?,\n");                 // 1. PICTURE
        query.append("      USER.USER_LAST_LOGIN = NOW(),\n");
        query.append("      USER.USER_LOGIN_COUNT = \n");
        query.append("      (\n");
        query.append("          SELECT\n");
        query.append("              USER.USER_LOGIN_COUNT\n");
        query.append("          FROM\n");
        query.append("              USER\n");
        query.append("          WHERE\n");
        query.append("              USER.USER_GUID = ?\n");        // 2. USER_GUID
        query.append("      ) + 1\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");                // 3. USER_GUID
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("userGuid: " + appUser.getUserGuid());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, googleUser.getPicture());
                        ps.setString(2, appUser.getUserGuid());
                        ps.setString(3, appUser.getUserGuid());
                        return ps;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
        }
    }
}

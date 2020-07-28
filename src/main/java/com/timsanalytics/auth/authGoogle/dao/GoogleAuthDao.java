package com.timsanalytics.auth.authGoogle.dao;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authGoogle.beans.GoogleUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
public class GoogleAuthDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Qualifier("mySqlAuthJdbcTemplate")
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    public GoogleAuthDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public void updateUserRecordFromGoogleAuth(User appUser, GoogleUser googleUser) {
        this.logger.debug("AppUserDao -> updateUserRecord: userGuid=" + appUser.getUserGuid());
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
            this.logger.error("GoogleAuthDao -> updateUserRecordFromGoogleAuth -> EmptyResultDataAccessException: " + e);
        } catch (Exception e) {
            this.logger.error("GoogleAuthDao -> updateUserRecordFromGoogleAuth -> Exception: " + e);
        }
    }
}

package com.timsanalytics.auth.authInternal.dao;

import com.timsanalytics.auth.authCommon.beans.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
public class InternalAuthDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Qualifier("mySqlAuthJdbcTemplate")
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    public InternalAuthDao(JdbcTemplate mySqlAuthJdbcTemplate) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
    }

    public void updateUserRecordFromInternalAuth(User appUser) {
        this.logger.trace("InternalAuthDao -> updateUserRecord: userGuid=" + appUser.getUserGuid());
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      AUTH.USER\n");
        query.append("  SET\n");
        query.append("      USER.USER_LAST_LOGIN = NOW(),\n");
        query.append("      USER.USER_LOGIN_COUNT = USER.USER_LOGIN_COUNT + 1\n");
        query.append("  WHERE\n");
        query.append("      USER.USER_GUID = ?\n");                // 3. USER_GUID
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("userGuid: " + appUser.getUserGuid());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, appUser.getUserGuid());
                        return ps;
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("InternalAuthDao -> updateUserRecordFromInternalAuth -> EmptyResultDataAccessException: " + e);
        } catch (Exception e) {
            this.logger.error("InternalAuthDao -> updateUserRecordFromInternalAuth -> Exception: " + e);
        }
    }
}

package com.timsanalytics.auth.authCommon.dao;

import com.timsanalytics.auth.authCommon.beans.User;
import com.timsanalytics.auth.authCommon.utils.GenerateUuidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
public class UserRoleDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final GenerateUuidService generateUuidService;

    @Qualifier("mySqlAuthJdbcTemplate")
    private final JdbcTemplate mySqlAuthJdbcTemplate;

    @Autowired
    public UserRoleDao(JdbcTemplate mySqlAuthJdbcTemplate, GenerateUuidService generateUuidService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
    }

    public String createUserRole(String userGuid, String roleGuid, String status, User loggedInUser) {
        this.logger.debug("UserRoleDao -> createUserRole: userGuid=" + userGuid + ", roleGuid=" + roleGuid);
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      USER_ROLE\n");
        query.append("  (\n");
        query.append("      USER_ROLE_GUID,\n");
        query.append("      USER_GUID,\n");
        query.append("      ROLE_GUID,\n");
        query.append("      STATUS,\n");
        query.append("      CREATED_ON,\n");
        query.append("      CREATED_BY,\n");
        query.append("      UPDATED_ON,\n");
        query.append("      UPDATED_BY\n");
        query.append("  )\n");
        query.append("  VALUES\n");
        query.append("  (\n");
        query.append("      ?,\n");
        query.append("      ?,\n");
        query.append("      ?,\n");
        query.append("      ?,\n");
        query.append("      NOW(),\n");
        query.append("      ?,\n");
        query.append("      NOW(),\n");
        query.append("      ?\n");
        query.append("  )\n");
        this.logger.debug("SQL:\n" + query.toString());
        String userRoleGuid = this.generateUuidService.GenerateUuid();
        this.logger.debug("appUserRoleGuid: " + userRoleGuid);
        this.logger.debug("userGuid: " + userGuid);
        this.logger.debug("roleGuid: " + roleGuid);

        this.mySqlAuthJdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(query.toString());
                    ps.setString(1, userRoleGuid);
                    ps.setString(2, userGuid);
                    ps.setString(3, roleGuid);
                    ps.setString(4, status);
                    ps.setString(5, loggedInUser.getUsername());
                    ps.setString(6, loggedInUser.getUsername());
                    return ps;
                });
        return userRoleGuid;
    }

    public String updateUserRole(String userGuid, String roleGuid, String status, User loggedInUser) {
        this.logger.debug("UserRoleDao -> updateUserRole: userGuid=" + userGuid + ", roleGuid=" + roleGuid);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE USER_ROLE\n");
        query.append("      SET\n");
        query.append("          STATUS = ?,\n");
        query.append("          UPDATED_ON = NOW(),\n");
        query.append("          UPDATED_BY = ?\n");
        query.append("  WHERE\n");
        query.append("      USER_GUID = ?\n");
        query.append("      AND ROLE_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());

        String userRoleGuid = this.generateUuidService.GenerateUuid();
        this.logger.debug("userRoleGuid: " + userRoleGuid);
        this.logger.debug("userGuid: " + userGuid);
        this.logger.debug("roleGuid: " + roleGuid);

        this.mySqlAuthJdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(query.toString());
                    ps.setString(1, status);
                    ps.setString(2, loggedInUser.getUsername());
                    ps.setString(3, userGuid);
                    ps.setString(4, roleGuid);
                    return ps;
                });
        return "success";
    }
}

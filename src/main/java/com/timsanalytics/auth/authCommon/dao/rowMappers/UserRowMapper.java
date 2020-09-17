package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User row = new User();
        row.setUserGuid(rs.getString("USER_GUID"));
        row.setUsername(rs.getString("USER_USERNAME"));
        row.setPassword(rs.getString("USER_PASSWORD"));
        row.setLoginCount(rs.getInt("USER_LOGIN_COUNT"));
        row.setLastLogin(rs.getTimestamp("USER_LAST_LOGIN"));

        row.setSurname(rs.getString("USER_SURNAME"));
        row.setGivenName(rs.getString("USER_GIVEN_NAME"));
        row.setPicture(rs.getString("USER_PROFILE_PHOTO_URL"));

        row.setStatus(rs.getString("STATUS"));
        row.setCreatedOn(rs.getTimestamp("CREATED_ON"));
        row.setCreatedBy(rs.getString("CREATED_BY"));
        row.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        row.setUpdatedBy(rs.getString("UPDATED_BY"));
        return row;
    }
}

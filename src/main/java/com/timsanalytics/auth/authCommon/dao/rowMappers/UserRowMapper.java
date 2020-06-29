package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User item = new User();
        item.setUserGuid(rs.getString("USER_GUID"));
        item.setUsername(rs.getString("USER_USERNAME"));
        item.setPassword(rs.getString("USER_PASSWORD"));
        item.setLoginCount(rs.getInt("USER_LOGIN_COUNT"));
        item.setLastLogin(rs.getTimestamp("USER_LAST_LOGIN"));

        item.setSurname(rs.getString("USER_SURNAME"));
        item.setGivenName(rs.getString("USER_GIVEN_NAME"));
        item.setPicture(rs.getString("USER_PROFILE_PHOTO_URL"));

        item.setStatus(rs.getString("STATUS"));
        item.setCreatedOn(rs.getTimestamp("CREATED_ON"));
        item.setCreatedBy(rs.getString("CREATED_BY"));
        item.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        item.setUpdatedBy(rs.getString("UPDATED_BY"));
        return item;
    }
}

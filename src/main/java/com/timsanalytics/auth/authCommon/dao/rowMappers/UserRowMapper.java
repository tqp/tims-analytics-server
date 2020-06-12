package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User item = new User();
        item.setUserGuid(rs.getString("USER_GUID"));
        item.setUsername(rs.getString("USERNAME"));
        item.setSurname(rs.getString("SURNAME"));
        item.setGivenName(rs.getString("GIVEN_NAME"));
        item.setPassword(rs.getString("PASSWORD"));
        item.setStatus(rs.getString("STATUS"));
        item.setTheme(rs.getString("THEME"));
        item.setPicture(rs.getString("PICTURE"));
        item.setLastLogin(rs.getTimestamp("LAST_LOGIN"));
        item.setLoginCount(rs.getInt("LOGIN_COUNT"));
        item.setCreatedOn(rs.getTimestamp("CREATED_ON"));
        item.setCreatedBy(rs.getString("CREATED_BY"));
        item.setUpdatedOn(rs.getTimestamp("UPDATED_ON"));
        item.setUpdatedBy(rs.getString("UPDATED_BY"));
        return item;
    }
}

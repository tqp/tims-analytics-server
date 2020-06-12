package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role item = new Role();
        item.setRoleGuid(rs.getString("ROLE_GUID"));
        item.setName(rs.getString("ROLE_NAME"));
        item.setAuthority(rs.getString("AUTHORITY"));
        return item;
    }
}

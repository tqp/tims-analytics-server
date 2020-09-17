package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper<Role> {

    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role row = new Role();
        row.setRoleGuid(rs.getString("ROLE_GUID"));
        row.setName(rs.getString("ROLE_NAME"));
        row.setAuthority(rs.getString("AUTHORITY"));
        return row;
    }
}

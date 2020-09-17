package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.Audit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditRowMapper implements RowMapper<Audit> {

    public Audit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Audit row = new Audit();
        row.setAuditGuid(rs.getString("AUDIT_GUID"));
        row.setType(rs.getString("TYPE"));
        row.setUsername(rs.getString("USERNAME"));
        row.setMethod(rs.getString("METHOD"));
        row.setRequest_uri(rs.getString("REQUEST_URI"));
        row.setRequest_body(rs.getString("REQUEST_BODY"));
        row.setResponse_time(rs.getLong("RESPONSE_TIME"));
        row.setResponse_code(rs.getInt("RESPONSE_CODE"));
        row.setTimestamp(rs.getTimestamp("TIMESTAMP"));
        return row;
    }
}

package com.timsanalytics.auth.authCommon.dao.rowMappers;

import com.timsanalytics.auth.authCommon.beans.Audit;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditRowMapper implements RowMapper<Audit> {

    public Audit mapRow(ResultSet rs, int rowNum) throws SQLException {
        Audit item = new Audit();
        item.setAuditGuid(rs.getString("AUDIT_GUID"));
        item.setType(rs.getString("TYPE"));
        item.setUsername(rs.getString("USERNAME"));
        item.setMethod(rs.getString("METHOD"));
        item.setRequest_uri(rs.getString("REQUEST_URI"));
        item.setRequest_body(rs.getString("REQUEST_BODY"));
        item.setResponse_time(rs.getLong("RESPONSE_TIME"));
        item.setResponse_code(rs.getInt("RESPONSE_CODE"));
        item.setTimestamp(rs.getTimestamp("TIMESTAMP"));
        return item;
    }
}

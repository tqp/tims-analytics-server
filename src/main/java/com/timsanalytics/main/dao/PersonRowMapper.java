package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person item = new Person();
        item.setGuid(rs.getString("GUID"));
        item.setFirstName(rs.getString("FIRST_NAME"));
        item.setLastName(rs.getString("LAST_NAME"));
        item.setCompanyName(rs.getString("COMPANY_NAME"));
        item.setAddress(rs.getString("ADDRESS"));
        item.setCity(rs.getString("CITY"));
        item.setCounty(rs.getString("COUNTY"));
        item.setState(rs.getString("STATE"));
        item.setZip(rs.getString("ZIP"));
        item.setPhone1(rs.getString("PHONE1"));
        item.setPhone2(rs.getString("PHONE2"));
        item.setEmail(rs.getString("EMAIL"));
        item.setWeb(rs.getString("WEB"));
        return item;
    }
}

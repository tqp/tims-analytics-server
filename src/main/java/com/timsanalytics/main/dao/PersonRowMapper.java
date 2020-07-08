package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person item = new Person();
        item.setGuid(rs.getString("PERSON_GUID"));
        item.setLastName(rs.getString("PERSON_LAST_NAME"));
        item.setFirstName(rs.getString("PERSON_FIRST_NAME"));
        item.setAddress(rs.getString("PERSON_STREET"));
        item.setCity(rs.getString("PERSON_CITY"));
        item.setCounty(rs.getString("PERSON_COUNTY"));
        item.setState(rs.getString("PERSON_STATE"));
        item.setZip(rs.getString("PERSON_ZIP_CODE"));
        item.setPhone1(rs.getString("PERSON_HOME_PHONE"));
        item.setPhone2(rs.getString("PERSON_MOBILE_PHONE"));
        item.setEmail(rs.getString("PERSON_EMAIL_ADDRESS"));
        item.setCompanyName(rs.getString("PERSON_COMPANY_NAME"));
        item.setWeb(rs.getString("PERSON_COMPANY_WEB_SITE"));
        return item;
    }
}

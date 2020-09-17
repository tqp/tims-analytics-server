package com.timsanalytics.apps.main.dao;

import com.timsanalytics.apps.main.beans.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person row = new Person();
        row.setGuid(rs.getString("PERSON_GUID"));
        row.setLastName(rs.getString("PERSON_LAST_NAME"));
        row.setFirstName(rs.getString("PERSON_FIRST_NAME"));
        row.setStreet(rs.getString("PERSON_STREET"));
        row.setCity(rs.getString("PERSON_CITY"));
        row.setCounty(rs.getString("PERSON_COUNTY"));
        row.setState(rs.getString("PERSON_STATE"));
        row.setZipCode(rs.getString("PERSON_ZIP_CODE"));
        row.setHomePhone(rs.getString("PERSON_HOME_PHONE"));
        row.setMobilePhone(rs.getString("PERSON_MOBILE_PHONE"));
        row.setEmailAddress(rs.getString("PERSON_EMAIL_ADDRESS"));
        row.setCompanyName(rs.getString("PERSON_COMPANY_NAME"));
        row.setCompanyWebsite(rs.getString("PERSON_COMPANY_WEB_SITE"));
        return row;
    }
}

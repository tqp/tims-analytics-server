package com.timsanalytics.main.dao;

import com.timsanalytics.main.beans.Person;
import com.timsanalytics.main.beans.ServerSidePaginationRequest;
import com.timsanalytics.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class PersonDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final PrintObjectService printObjectService;

    @Autowired
    public PersonDao(JdbcTemplate mySqlAuthJdbcTemplate, PrintObjectService printObjectService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.printObjectService = printObjectService;
    }

    public List<Person> getPersonList_All() {
        this.logger.trace("PersonDao -> getPersonList_All");
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON.PERSON_GUID,\n");
        query.append("      PERSON.PERSON_LAST_NAME,\n");
        query.append("      PERSON.PERSON_FIRST_NAME,\n");
        query.append("      PERSON.PERSON_STREET,\n");
        query.append("      PERSON.PERSON_CITY,\n");
        query.append("      PERSON.PERSON_COUNTY,\n");
        query.append("      PERSON.PERSON_STATE,\n");
        query.append("      PERSON.PERSON_ZIP_CODE,\n");
        query.append("      PERSON.PERSON_HOME_PHONE,\n");
        query.append("      PERSON.PERSON_MOBILE_PHONE,\n");
        query.append("      PERSON.PERSON_EMAIL_ADDRESS,\n");
        query.append("      PERSON.PERSON_COMPANY_NAME,\n");
        query.append("      PERSON.PERSON_COMPANY_WEB_SITE\n");
        query.append("  FROM\n");
        query.append("      PERSON\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Person> getPersonList_InfiniteScroll(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.logger.debug("PersonDao -> getPersonList_InfiniteScroll");
        this.printObjectService.PrintObject("serverSidePaginationRequest", serverSidePaginationRequest);

        int pageStart = (serverSidePaginationRequest.getPageIndex() - 1) * (serverSidePaginationRequest.getPageSize() + 1);
        int pageEnd = (pageStart + serverSidePaginationRequest.getPageSize() - 1);
        String filter = serverSidePaginationRequest.getFilter() != null ? serverSidePaginationRequest.getFilter() : "";

        StringBuilder query = new StringBuilder();

        query.append("  -- PAGINATION QUERY\n");
        query.append("  SELECT\n");
        query.append("      FILTER_SORT_QUERY.*\n");
        query.append("  FROM\n");

        query.append("      -- FILTER/SORT QUERY\n");
        query.append("      (\n");
        query.append("          SELECT\n");
        query.append("              *\n");
        query.append("          FROM\n");

        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getPersonList_InfiniteScroll_RootQuery());
        query.append("              WHERE\n");
        query.append("              (\n");
        query.append(getPersonList_InfiniteScroll_WhereClause(filter));
        query.append("              )\n");
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append("              PERSON_LAST_NAME,\n");
        query.append("              PERSON_FIRST_NAME\n");
        query.append("      ) AS FILTER_SORT_QUERY\n");
        query.append("      -- END FILTER/SORT QUERY\n");

        query.append("  LIMIT ?, ?\n");
        query.append("  -- END PAGINATION QUERY\n");
        this.logger.debug("SQL:\n" + query.toString());
        this.logger.debug("pageStart=" + pageStart + ", pageEnd=" + pageEnd);
        this.logger.debug("filter: " + filter);

        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{
                    pageStart,
                    pageEnd
            }, (rs, rowNum) -> {
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
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private String getPersonList_InfiniteScroll_RootQuery() {
        return "" +
                "SELECT DISTINCT" +
                "   PERSON.PERSON_GUID,\n" +
                "   PERSON.PERSON_LAST_NAME,\n" +
                "   PERSON.PERSON_FIRST_NAME,\n" +
                "   PERSON.PERSON_STREET,\n" +
                "   PERSON.PERSON_CITY,\n" +
                "   PERSON.PERSON_COUNTY,\n" +
                "   PERSON.PERSON_STATE,\n" +
                "   PERSON.PERSON_ZIP_CODE,\n" +
                "   PERSON.PERSON_HOME_PHONE,\n" +
                "   PERSON.PERSON_MOBILE_PHONE,\n" +
                "   PERSON.PERSON_EMAIL_ADDRESS,\n" +
                "   PERSON.PERSON_COMPANY_NAME,\n" +
                "   PERSON.PERSON_COMPANY_WEB_SITE\n" +
                "FROM\n" +
                "   SAMPLE_DATA.PERSON\n";
    }

    private String getPersonList_InfiniteScroll_WhereClause(String filter) {
        this.logger.debug("PersonDao -> getPersonList_InfiniteScroll_WhereClause: filter=" + filter);
        StringBuilder whereClause = new StringBuilder();

        // IF a table-wide filter exists, search all 'searchable' fields.
        if (!"".equalsIgnoreCase(filter)) {
            whereClause.append("(\n");
            whereClause.append("    UPPER(PERSON.PERSON_LAST_NAME) LIKE UPPER('%").append(filter).append("%')\n");
            whereClause.append("    OR");
            whereClause.append("    UPPER(PERSON.PERSON_FIRST_NAME) LIKE UPPER('%").append(filter).append("%')\n");
            whereClause.append(")\n");
        } else {
            whereClause.append("(1=1)");
        }
        return whereClause.toString();
    }

    public Person getPersonDetail(String personGuid) {
        this.logger.trace("PersonDao -> getPersonDetail: personGuid=" + personGuid);
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      PERSON_GUID,\n");
        query.append("      PERSON_LAST_NAME,\n");
        query.append("      PERSON_FIRST_NAME,\n");
        query.append("      PERSON_STREET,\n");
        query.append("      PERSON_CITY,\n");
        query.append("      PERSON_STATE,\n");
        query.append("      PERSON_ZIP_CODE,\n");
        query.append("      PERSON_COUNTY,\n");
        query.append("      PERSON_HOME_PHONE,\n");
        query.append("      PERSON_MOBILE_PHONE,\n");
        query.append("      PERSON_EMAIL_ADDRESS,\n");
        query.append("      PERSON_COMPANY_NAME,\n");
        query.append("      PERSON_COMPANY_WEB_SITE\n");
        query.append("  FROM\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  WHERE\n");
        query.append("      PERSON_GUID = ?;\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{personGuid}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Person updatePerson(Person person) {
        this.logger.debug("PersonDao -> updatePerson");
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      SAMPLE_DATA.PERSON\n");
        query.append("  SET\n");
        query.append("      PERSON.PERSON_LAST_NAME = ?,\n");
        query.append("      PERSON.PERSON_FIRST_NAME = ?\n");
        query.append("  WHERE\n");
        query.append("      PERSON.PERSON_GUID = ?\n");
        this.logger.debug("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, person.getLastName());
                        ps.setString(2, person.getFirstName());
                        ps.setString(3, person.getGuid());
                        return ps;
                    }
            );
            return this.getPersonDetail(person.getGuid());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

}

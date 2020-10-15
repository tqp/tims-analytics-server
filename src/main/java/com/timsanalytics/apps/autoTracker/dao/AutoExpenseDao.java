package com.timsanalytics.apps.autoTracker.dao;

import com.timsanalytics.apps.autoTracker.beans.Expense;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.utils.GenerateUuidService;
import com.timsanalytics.common.utils.PrintObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service
public class AutoExpenseDao {
    private final Logger logger = LoggerFactory.getLogger(getClass().getName());
    private final JdbcTemplate mySqlAuthJdbcTemplate;
    private final GenerateUuidService generateUuidService;
    private final PrintObjectService printObjectService;

    @Autowired
    public AutoExpenseDao(JdbcTemplate mySqlAuthJdbcTemplate,
                          GenerateUuidService generateUuidService,
                          PrintObjectService printObjectService) {
        this.mySqlAuthJdbcTemplate = mySqlAuthJdbcTemplate;
        this.generateUuidService = generateUuidService;
        this.printObjectService = printObjectService;
    }

    public Expense createAutoExpense(Expense expense) {
        this.printObjectService.PrintObject("autoExpense", expense);
        StringBuilder query = new StringBuilder();
        query.append("  INSERT INTO\n");
        query.append("      AUTO_TRACKER.EXPENSE\n");
        query.append("      (\n");
        query.append("          EXPENSE.EXPENSE_GUID,\n");
        query.append("          EXPENSE.EXPENSE_COMMENTS,\n");
        query.append("          EXPENSE.STATUS\n");
        query.append("      )\n");
        query.append("      VALUES\n");
        query.append("      (\n");
        query.append("          ?,\n");
        query.append("          ?,\n");
        query.append("          'Active'\n");
        query.append("      )\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        expense.setExpenseGuid(this.generateUuidService.GenerateUuid());
                        this.logger.debug("New Expense GUID: " + expense.getExpenseGuid());
                        ps.setString(1, expense.getExpenseGuid());
                        ps.setString(2, expense.getExpenseComments());
                        return ps;
                    }
            );
            return expense;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public List<Expense> getExpenseList() {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      EXPENSE_GUID,\n");
        query.append("      EXPENSE_COMMENTS\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.EXPENSES\n");
        query.append("  WHERE\n");
        query.append("      STATUS = 'Active'\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{},
                    (rs, rowNum) -> {
                        Expense expense = new Expense();
                        expense.setExpenseGuid(rs.getString("EXPENSE_GUID"));
                        expense.setExpenseComments(rs.getString("EXPENSE_COMMENTS"));
                        return expense;
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getExpenseList_SSP_TotalRecords(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder query = new StringBuilder();
        query.append("          SELECT\n");
        query.append("              COUNT(*)\n");
        query.append("          FROM\n");
        query.append("          -- ROOT QUERY\n");
        query.append("          (\n");
        query.append(getAutoExpenseList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");
        try {
            Integer count = this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{}, Integer.class);
            return count == null ? 0 : count;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return 0;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return 0;
        }
    }

    public List<Expense> getExpenseList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        int pageStart = (serverSidePaginationRequest.getPageIndex()) * serverSidePaginationRequest.getPageSize();
        int pageSize = serverSidePaginationRequest.getPageSize();

        String sortColumn = serverSidePaginationRequest.getSortColumn();
        String sortDirection = serverSidePaginationRequest.getSortDirection();

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
        query.append(getAutoExpenseList_SSP_RootQuery(serverSidePaginationRequest));
        query.append("          ) AS ROOT_QUERY\n");
        query.append("          -- END ROOT QUERY\n");

        query.append("          ORDER BY\n");
        query.append(sortColumn).append(" ").append(sortDirection.toUpperCase()).append(",\n");
        query.append("              EXPENSE_DATE DESC\n"); // UPDATE THIS WHEN USING AS TEMPLATE!
        query.append("      ) AS FILTER_SORT_QUERY\n");
        query.append("      -- END FILTER/SORT QUERY\n");

        query.append("  LIMIT ?, ?\n");
        query.append("  -- END PAGINATION QUERY\n");

        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("pageStart=" + pageStart + ", pageSize=" + pageSize);

        try {
            return this.mySqlAuthJdbcTemplate.query(query.toString(), new Object[]{
                    pageStart,
                    pageSize
            }, (rs, rowNum) -> {
                Expense expense = new Expense();
                expense.setExpenseGuid(rs.getString("EXPENSE_GUID"));
                expense.setExpenseTypeGuid(rs.getString("EXPENSE_TYPE_GUID"));
                expense.setExpenseTypeName(rs.getString("EXPENSE_TYPE_NAME"));
                expense.setExpenseDate(rs.getDate("EXPENSE_DATE"));
                expense.setExpenseAmount(rs.getDouble("EXPENSE_AMOUNT"));
                expense.setExpenseComments(rs.getString("EXPENSE_COMMENTS"));
                return expense;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    private String getAutoExpenseList_SSP_RootQuery(ServerSidePaginationRequest serverSidePaginationRequest) {
        //noinspection StringBufferReplaceableByString
        StringBuilder query = new StringBuilder();
        query.append("              SELECT\n");
        query.append("                  EXPENSE_GUID,\n");
        query.append("                  EXPENSE.EXPENSE_TYPE_GUID,\n");
        query.append("                  EXPENSE_TYPE.EXPENSE_TYPE_NAME,\n");
        query.append("                  EXPENSE_DATE,\n");
        query.append("                  EXPENSE_AMOUNT,\n");
        query.append("                  EXPENSE_COMMENTS\n");
        query.append("              FROM\n");
        query.append("                  AUTO_TRACKER.EXPENSE EXPENSE\n");
        query.append("                  LEFT JOIN AUTO_TRACKER.EXPENSE_TYPE ON EXPENSE_TYPE.EXPENSE_TYPE_GUID = EXPENSE.EXPENSE_TYPE_GUID\n");
        query.append("              WHERE\n");
        query.append("              (\n");
        query.append("                  STATUS = 'Active'\n");
        query.append("                  AND\n");
        query.append(getAutoExpenseList_SSP_AdditionalWhereClause(serverSidePaginationRequest));
        query.append("              )");
        return query.toString();
    }

    private String getAutoExpenseList_SSP_AdditionalWhereClause(ServerSidePaginationRequest serverSidePaginationRequest) {
        StringBuilder whereClause = new StringBuilder();
        String nameFilter = serverSidePaginationRequest.getNameFilter() != null ? serverSidePaginationRequest.getNameFilter() : "";

        // NAME FILTER CLAUSE
        if (!"".equalsIgnoreCase(nameFilter)) {
            whereClause.append("                  (2=2)");
//            whereClause.append("                  (\n");
//            whereClause.append("                    UPPER(FUEL_ACTIVITY.CONTESTANT_LAST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
//            whereClause.append("                    OR");
//            whereClause.append("                    UPPER(FUEL_ACTIVITY.CONTESTANT_FIRST_NAME) LIKE UPPER('%").append(nameFilter).append("%')\n");
//            whereClause.append("                  )\n");
        } else {
            whereClause.append("                  (1=1)");
        }

        return whereClause.toString();
    }

    public Expense getExpenseDetail(String autoExpenseGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  SELECT\n");
        query.append("      EXPENSE_GUID,\n");
        query.append("      EXPENSE.EXPENSE_TYPE_GUID,\n");
        query.append("      EXPENSE_TYPE.EXPENSE_TYPE_NAME,\n");
        query.append("      EXPENSE_DATE,\n");
        query.append("      EXPENSE_AMOUNT,\n");
        query.append("      EXPENSE_COMMENTS\n");
        query.append("  FROM\n");
        query.append("      AUTO_TRACKER.EXPENSE\n");
        query.append("      LEFT JOIN AUTO_TRACKER.EXPENSE_TYPE ON EXPENSE_TYPE.EXPENSE_TYPE_GUID = EXPENSE.EXPENSE_TYPE_GUID\n");
        query.append("  WHERE\n");
        query.append("      EXPENSE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            return this.mySqlAuthJdbcTemplate.queryForObject(query.toString(), new Object[]{autoExpenseGuid}, (rs, rowNum) -> {
                Expense expense = new Expense();
                expense.setExpenseGuid(rs.getString("EXPENSE_GUID"));
                expense.setExpenseTypeGuid(rs.getString("EXPENSE_TYPE_GUID"));
                expense.setExpenseTypeName(rs.getString("EXPENSE_TYPE_NAME"));
                expense.setExpenseDate(rs.getDate("EXPENSE_DATE"));
                expense.setExpenseAmount(rs.getDouble("EXPENSE_AMOUNT"));
                expense.setExpenseComments(rs.getString("EXPENSE_COMMENTS"));
                return expense;
            });
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public Expense updateExpense(Expense expense) {
        this.printObjectService.PrintObject("expense", expense);
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      AUTO_TRACKER.EXPENSE\n");
        query.append("  SET\n");
        query.append("      EXPENSE.EXPENSE_COMMENTS = ?\n");
        query.append("  WHERE\n");
        query.append("      EXPENSE.EXPENSE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, expense.getExpenseComments());
                        ps.setString(2, expense.getExpenseGuid());
                        return ps;
                    }
            );
            return expense;
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }

    public KeyValue deleteExpense(String expenseGuid) {
        StringBuilder query = new StringBuilder();
        query.append("  UPDATE\n");
        query.append("      AUTO_TRACKER.EXPENSE\n");
        query.append("  SET\n");
        query.append("      STATUS = 'Deleted'\n");
        query.append("  WHERE\n");
        query.append("      EXPENSE_GUID = ?\n");
        this.logger.trace("SQL:\n" + query.toString());
        this.logger.trace("EXPENSE_GUID=" + expenseGuid);
        try {
            this.mySqlAuthJdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(query.toString());
                        ps.setString(1, expenseGuid);
                        return ps;
                    }
            );
            return new KeyValue("expenseGuid", expenseGuid);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("EmptyResultDataAccessException: " + e);
            return null;
        } catch (Exception e) {
            this.logger.error("Exception: " + e);
            return null;
        }
    }
}

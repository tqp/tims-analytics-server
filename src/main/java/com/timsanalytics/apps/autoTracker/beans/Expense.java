package com.timsanalytics.apps.autoTracker.beans;

import java.sql.Date;

public class Expense {
    private String expenseGuid;
    private String expenseComments;
    private Date expenseDate;
    private Double expenseAmount;
    private String expenseTypeGuid;
    private String expenseTypeName;
    private String expenseType;

    public String getExpenseGuid() {
        return expenseGuid;
    }

    public void setExpenseGuid(String expenseGuid) {
        this.expenseGuid = expenseGuid;
    }

    public String getExpenseComments() {
        return expenseComments;
    }

    public void setExpenseComments(String expenseComments) {
        this.expenseComments = expenseComments;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseTypeGuid() {
        return expenseTypeGuid;
    }

    public void setExpenseTypeGuid(String expenseTypeGuid) {
        this.expenseTypeGuid = expenseTypeGuid;
    }

    public String getExpenseTypeName() {
        return expenseTypeName;
    }

    public void setExpenseTypeName(String expenseTypeName) {
        this.expenseTypeName = expenseTypeName;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }
}

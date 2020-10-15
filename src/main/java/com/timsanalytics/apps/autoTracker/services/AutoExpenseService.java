package com.timsanalytics.apps.autoTracker.services;

import com.timsanalytics.apps.autoTracker.beans.Expense;
import com.timsanalytics.apps.autoTracker.dao.AutoExpenseDao;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoExpenseService {
    private final AutoExpenseDao autoExpenseDao;

    @Autowired
    public AutoExpenseService(AutoExpenseDao autoExpenseDao) {
        this.autoExpenseDao = autoExpenseDao;
    }

    public Expense createAutoExpense(Expense expense) {
        return this.autoExpenseDao.createAutoExpense(expense);
    }

    public List<Expense> getAutoExpenseList() {
        return this.autoExpenseDao.getExpenseList();
    }

    public ServerSidePaginationResponse<Expense> getAutoExpenseList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<Expense> serverSidePaginationResponse = new ServerSidePaginationResponse<>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Expense> expenseList = this.autoExpenseDao.getExpenseList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(expenseList);
        serverSidePaginationResponse.setLoadedRecords(expenseList.size());
        serverSidePaginationResponse.setTotalRecords(this.autoExpenseDao.getExpenseList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
    }

    public Expense getAutoExpenseDetail(String autoExpenseGuid) {
        return this.autoExpenseDao.getExpenseDetail(autoExpenseGuid);
    }

    public Expense updateAutoExpense(Expense expense) {
        return this.autoExpenseDao.updateExpense(expense);
    }

    public KeyValue deleteAutoExpense(String autoExpenseGuid) {
        return this.autoExpenseDao.deleteExpense(autoExpenseGuid);
    }
}

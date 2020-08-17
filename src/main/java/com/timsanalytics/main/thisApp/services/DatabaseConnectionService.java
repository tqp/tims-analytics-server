package com.timsanalytics.main.thisApp.services;

import com.timsanalytics.common.beans.KeyValueString;
import com.timsanalytics.main.thisApp.dao.DatabaseConnectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectionService {
    private final DatabaseConnectionDao databaseConnectionDao;

    @Autowired
    public DatabaseConnectionService(DatabaseConnectionDao databaseConnectionDao) {
        this.databaseConnectionDao = databaseConnectionDao;
    }

    public KeyValueString getDatabaseResponse() {
        return this.databaseConnectionDao.getDatabaseResponse();
    }
}

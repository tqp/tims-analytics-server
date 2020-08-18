package com.timsanalytics.apps.main.services;

import com.timsanalytics.apps.main.dao.DatabaseConnectionDao;
import com.timsanalytics.common.beans.KeyValueString;
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

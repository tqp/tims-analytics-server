package com.timsanalytics.main.services;

import com.timsanalytics.main.beans.KeyValueString;
import com.timsanalytics.main.dao.DatabaseConnectionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseConnectionService {
    private final DatabaseConnectionDao databaseConnectionDao;

    @Autowired
    public DatabaseConnectionService(DatabaseConnectionDao databaseConnectionDao) {
        this.databaseConnectionDao = databaseConnectionDao;
    }

    public List<KeyValueString> getDatabaseResponse() {
        return this.databaseConnectionDao.getDatabaseResponse();
    }
}

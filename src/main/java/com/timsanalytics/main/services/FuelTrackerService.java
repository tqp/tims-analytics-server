package com.timsanalytics.main.services;

import com.timsanalytics.main.beans.KeyValueLong;
import com.timsanalytics.main.dao.FuelTrackerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelTrackerService {
    private final FuelTrackerDao fuelTrackerDao;

    @Autowired
    public FuelTrackerService(FuelTrackerDao fuelTrackerDao) {
        this.fuelTrackerDao = fuelTrackerDao;
    }

    public List<KeyValueLong> getFuelInfo() {
        return this.fuelTrackerDao.getFuelInfo();
    }
}

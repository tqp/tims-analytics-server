package com.timsanalytics.main.services;

import com.timsanalytics.main.beans.KeyValueDouble;
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

    public Double getLongestDistance() {
        return this.fuelTrackerDao.getLongestDistance();
    }

    public List<KeyValueDouble> getOdometerData() {
        return this.fuelTrackerDao.getOdometerData();
    }

    public List<KeyValueDouble> getMpgData() {
        return this.fuelTrackerDao.getMpgData();
    }
}

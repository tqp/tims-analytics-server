package com.timsanalytics.apps.autoTracker.services;

import com.timsanalytics.apps.autoTracker.beans.Station;
import com.timsanalytics.apps.autoTracker.dao.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    private final StationDao stationDao;

    @Autowired
    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public List<Station> getAutoCompleteStationName(String filter) {
        return this.stationDao.getAutoCompleteStationName(filter);
    }
}

package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.main.realityTracker.beans.Season;
import com.timsanalytics.main.realityTracker.dao.SeasonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {
    private final SeasonDao seasonDao;

    @Autowired
    public SeasonService(SeasonDao seasonDao) {
        this.seasonDao = seasonDao;
    }

    public List<Season> getSeriesSeasonList(String seriesGuid) {
        return this.seasonDao.getSeriesSeasonList(seriesGuid);
    }
}

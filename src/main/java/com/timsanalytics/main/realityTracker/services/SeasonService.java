package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Player;
import com.timsanalytics.main.realityTracker.beans.Season;
import com.timsanalytics.main.realityTracker.beans.Series;
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

    public Season createSeason(Season season) {
        return this.seasonDao.createSeason(season);
    }

    public List<Season> getSeasonListBySeriesGuid(String seriesGuid) {
        return this.seasonDao.getSeasonListBySeriesGuid(seriesGuid);
    }

    public Season getSeasonDetail(String seasonGuid) {
        return this.seasonDao.getSeasonDetail(seasonGuid);
    }

    public Season updateSeason(Season season) {
        return this.seasonDao.updateSeason(season);
    }

    public KeyValue deleteSeason(String seasonGuid) {
        return this.seasonDao.deleteSeason(seasonGuid);
    }
}

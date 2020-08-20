package com.timsanalytics.apps.realityTracker.services;

import com.timsanalytics.apps.realityTracker.beans.Episode;
import com.timsanalytics.apps.realityTracker.dao.EpisodeDao;
import com.timsanalytics.auth.authCommon.beans.KeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {
    private final EpisodeDao episodeDao;

    @Autowired
    public EpisodeService(EpisodeDao episodeDao) {
        this.episodeDao = episodeDao;
    }

    public Episode createEpisode(Episode episode) {
        return this.episodeDao.createEpisode(episode);
    }

    public List<Episode> getEpisodeListFiltered(String seasonGuid) {
        return this.episodeDao.getEpisodeListFiltered(seasonGuid);
    }

    public Episode getEpisodeDetail(String episodeGuid) {
        return this.episodeDao.getEpisodeDetail(episodeGuid);
    }

    public Episode updateEpisode(Episode episode) {
        return this.episodeDao.updateEpisode(episode);
    }

    public KeyValue deleteEpisode(String episodeGuid) {
        return this.episodeDao.deleteEpisode(episodeGuid);
    }
}

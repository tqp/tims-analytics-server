package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.realityTracker.beans.Season;
import com.timsanalytics.main.realityTracker.beans.Series;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponseSeries;
import com.timsanalytics.main.realityTracker.dao.SeriesDao;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private final SeriesDao seriesDao;

    @Autowired
    public SeriesService(SeriesDao seriesDao) {
        this.seriesDao = seriesDao;
    }

    public Series createSeries(Series series) {
        return this.seriesDao.createSeries(series);
    }

    public ServerSidePaginationResponseSeries getSeriesList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponseSeries serverSidePaginationResponseSeries = new ServerSidePaginationResponseSeries();
        serverSidePaginationResponseSeries.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Series> seriesList = this.seriesDao.getSeriesList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponseSeries.setData(seriesList);
        serverSidePaginationResponseSeries.setLoadedRecords(seriesList.size());
        serverSidePaginationResponseSeries.setTotalRecords(this.seriesDao.getSeriesList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponseSeries;
    }

    public Series getSeriesDetail(String seriesGuid) {
        return this.seriesDao.getSeriesDetail(seriesGuid);
    }

    public Series updateSeries(Series contestant) {
        return this.seriesDao.updateSeries(contestant);
    }

    public KeyValue deleteSeries(String seriesGuid) {
        return this.seriesDao.deleteSeries(seriesGuid);
    }
}

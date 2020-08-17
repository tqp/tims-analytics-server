package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Series;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponse;
import com.timsanalytics.main.realityTracker.dao.SeriesDao;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
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

    public ServerSidePaginationResponse<Series> getSeriesList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<Series> serverSidePaginationResponse = new ServerSidePaginationResponse<Series>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Series> seriesList = this.seriesDao.getSeriesList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(seriesList);
        serverSidePaginationResponse.setLoadedRecords(seriesList.size());
        serverSidePaginationResponse.setTotalRecords(this.seriesDao.getSeriesList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
    }

    public Series getSeriesDetail(String seriesGuid) {
        return this.seriesDao.getSeriesDetail(seriesGuid);
    }

    public Series updateSeries(Series series) {
        return this.seriesDao.updateSeries(series);
    }

    public KeyValue deleteSeries(String seriesGuid) {
        return this.seriesDao.deleteSeries(seriesGuid);
    }
}

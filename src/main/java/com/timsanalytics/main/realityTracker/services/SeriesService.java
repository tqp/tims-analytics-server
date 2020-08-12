package com.timsanalytics.main.realityTracker.services;

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

    public ServerSidePaginationResponseSeries getSeriesList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponseSeries serverSidePaginationResponseSeries = new ServerSidePaginationResponseSeries();
        serverSidePaginationResponseSeries.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Series> seriesList = this.seriesDao.getSeriesList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponseSeries.setData(seriesList);
        serverSidePaginationResponseSeries.setLoadedRecords(seriesList.size());
        serverSidePaginationResponseSeries.setTotalRecords(this.seriesDao.getSeriesList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponseSeries;
    }
}

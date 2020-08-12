package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponseContestant;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponseSeries;
import com.timsanalytics.main.realityTracker.dao.ContestantDao;
import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestantService {
    private final ContestantDao contestantDao;

    @Autowired
    public ContestantService(ContestantDao contestantDao) {
        this.contestantDao = contestantDao;
    }

    public ServerSidePaginationResponseContestant getContestantList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponseContestant serverSidePaginationResponseContestant = new ServerSidePaginationResponseContestant();
        serverSidePaginationResponseContestant.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Contestant> contestantList = this.contestantDao.getContestantList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponseContestant.setData(contestantList);
        serverSidePaginationResponseContestant.setLoadedRecords(contestantList.size());
        serverSidePaginationResponseContestant.setTotalRecords(this.contestantDao.getContestantList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponseContestant;
    }
}

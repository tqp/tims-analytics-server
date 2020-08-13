package com.timsanalytics.main.realityTracker.services;

import com.timsanalytics.auth.authCommon.beans.KeyValue;
import com.timsanalytics.main.realityTracker.beans.Contestant;
import com.timsanalytics.main.realityTracker.beans.ServerSidePaginationResponse;
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

    public Contestant createContestant(Contestant contestant) {
        return this.contestantDao.createContestant(contestant);
    }

    public ServerSidePaginationResponse<Contestant> getContestantList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<Contestant> serverSidePaginationResponse = new ServerSidePaginationResponse<Contestant>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<Contestant> contestantList = this.contestantDao.getContestantList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(contestantList);
        serverSidePaginationResponse.setLoadedRecords(contestantList.size());
        serverSidePaginationResponse.setTotalRecords(this.contestantDao.getContestantList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
    }

    public Contestant getContestantDetail(String contestantGuid) {
        return this.contestantDao.getContestantDetail(contestantGuid);
    }

    public Contestant updateContestant(Contestant contestant) {
        return this.contestantDao.updateContestant(contestant);
    }

    public KeyValue deleteContestant(String contestantGuid) {
        return this.contestantDao.deleteContestant(contestantGuid);
    }
}

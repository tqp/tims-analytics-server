package com.timsanalytics.apps.autoTracker.services;

import com.timsanalytics.apps.autoTracker.beans.Fill;
import com.timsanalytics.apps.autoTracker.beans.FuelActivity;
import com.timsanalytics.apps.autoTracker.dao.FuelActivityDao;
import com.timsanalytics.common.beans.KeyValue;
import com.timsanalytics.common.beans.ServerSidePaginationRequest;
import com.timsanalytics.common.beans.ServerSidePaginationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelActivityService {
    private final FuelActivityDao fuelActivityDao;

    @Autowired
    public FuelActivityService(FuelActivityDao fuelActivityDao) {
        this.fuelActivityDao = fuelActivityDao;
    }

    public Fill createFuelActivity(Fill fill) {
        return this.fuelActivityDao.createFuelActivity(fill);
    }

    public List<FuelActivity> getFuelActivityList() {
        return this.fuelActivityDao.getFuelActivityList();
    }

    public ServerSidePaginationResponse<FuelActivity> getFuelActivityList_SSP(ServerSidePaginationRequest serverSidePaginationRequest) {
        ServerSidePaginationResponse<FuelActivity> serverSidePaginationResponse = new ServerSidePaginationResponse<>();
        serverSidePaginationResponse.setServerSidePaginationRequest(serverSidePaginationRequest);
        List<FuelActivity> fuelActivityList = this.fuelActivityDao.getFuelActivityList_SSP(serverSidePaginationRequest);
        serverSidePaginationResponse.setData(fuelActivityList);
        serverSidePaginationResponse.setLoadedRecords(fuelActivityList.size());
        serverSidePaginationResponse.setTotalRecords(this.fuelActivityDao.getFuelActivityList_SSP_TotalRecords(serverSidePaginationRequest));
        return serverSidePaginationResponse;
    }

    public FuelActivity getFuelActivityDetail(String fuelActivityGuid) {
        return this.fuelActivityDao.getFuelActivityDetail(fuelActivityGuid);
    }

    public Fill updateFuelActivity(Fill fill) {
        return this.fuelActivityDao.updateFuelActivity(fill);
    }

    public KeyValue deleteFuelActivity(String fuelActivityGuid) {
        return this.fuelActivityDao.deleteFuelActivity(fuelActivityGuid);
    }
}

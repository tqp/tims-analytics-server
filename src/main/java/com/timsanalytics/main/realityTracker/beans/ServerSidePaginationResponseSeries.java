package com.timsanalytics.main.realityTracker.beans;

import com.timsanalytics.main.thisApp.beans.ServerSidePaginationRequest;

import java.util.List;

public class ServerSidePaginationResponseSeries {
    private Long requestTime;
    private Integer loadedRecords;
    private Integer totalRecords;
    private List<Series> data;
    private ServerSidePaginationRequest serverSidePaginationRequest;

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getLoadedRecords() {
        return loadedRecords;
    }

    public void setLoadedRecords(Integer loadedRecords) {
        this.loadedRecords = loadedRecords;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Series> getData() {
        return data;
    }

    public void setData(List<Series> data) {
        this.data = data;
    }

    public ServerSidePaginationRequest getServerSidePaginationRequest() {
        return serverSidePaginationRequest;
    }

    public void setServerSidePaginationRequest(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.serverSidePaginationRequest = serverSidePaginationRequest;
    }
}

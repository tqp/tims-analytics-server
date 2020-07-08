package com.timsanalytics.main.beans;

import java.util.List;

public class ServerSidePaginationResponse {
    private Long requestTime;
    private Integer length;
    private List<Person> data;
    private ServerSidePaginationRequest serverSidePaginationRequest;

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
    }

    public ServerSidePaginationRequest getServerSidePaginationRequest() {
        return serverSidePaginationRequest;
    }

    public void setServerSidePaginationRequest(ServerSidePaginationRequest serverSidePaginationRequest) {
        this.serverSidePaginationRequest = serverSidePaginationRequest;
    }
}

package com.timsanalytics.main.realityTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Season {
    private String guid;
    private String name;
    private Date startDate;
    private String seriesGuid;
    private String seriesName;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSeriesGuid() {
        return seriesGuid;
    }

    public void setSeriesGuid(String seriesGuid) {
        this.seriesGuid = seriesGuid;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}

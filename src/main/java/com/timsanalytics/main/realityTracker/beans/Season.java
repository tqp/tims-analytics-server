package com.timsanalytics.main.realityTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Season {
    private String seasonGuid;
    private String seriesGuid;
    private int number;

    public String getSeasonGuid() {
        return seasonGuid;
    }

    public void setSeasonGuid(String seasonGuid) {
        this.seasonGuid = seasonGuid;
    }

    public String getSeriesGuid() {
        return seriesGuid;
    }

    public void setSeriesGuid(String seriesGuid) {
        this.seriesGuid = seriesGuid;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

package com.timsanalytics.apps.autoTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Station {
    private String stationGuid;
    private String stationName;
    private String stationAffiliation;
    private String stationAddress;
    private String stationCity;
    private String stationState;
    private String stationZip;
    private String stationPhone;
    private Integer stationVisitCount;

    public String getStationGuid() {
        return stationGuid;
    }

    public void setStationGuid(String stationGuid) {
        this.stationGuid = stationGuid;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAffiliation() {
        return stationAffiliation;
    }

    public void setStationAffiliation(String stationAffiliation) {
        this.stationAffiliation = stationAffiliation;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationCity() {
        return stationCity;
    }

    public void setStationCity(String stationCity) {
        this.stationCity = stationCity;
    }

    public String getStationState() {
        return stationState;
    }

    public void setStationState(String stationState) {
        this.stationState = stationState;
    }

    public String getStationZip() {
        return stationZip;
    }

    public void setStationZip(String stationZip) {
        this.stationZip = stationZip;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public Integer getStationVisitCount() {
        return stationVisitCount;
    }

    public void setStationVisitCount(Integer stationVisitCount) {
        this.stationVisitCount = stationVisitCount;
    }
}

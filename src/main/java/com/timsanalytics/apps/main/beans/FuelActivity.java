package com.timsanalytics.apps.main.beans;

import java.sql.Date;

public class FuelActivity {
    private String fuelActivityGuid;
    private Date fuelActivityDate;
    private Long fuelActivityOdometer;
    private Double fuelActivityGallons;
    private Double fuelActivityCostPerGallon;
    private Double fuelActivityTotalCost;
    private Double fuelActivityMilesTraveled;
    private Double fuelActivityMilesPerGallon;
    private String fuelActivityComments;

    private String stationGuid;
    private String stationName;
    private String stationAffiliation;
    private String stationAddress;
    private String stationCity;
    private String stationState;
    private String stationZip;
    private String stationPhone;

    public String getFuelActivityGuid() {
        return fuelActivityGuid;
    }

    public void setFuelActivityGuid(String fuelActivityGuid) {
        this.fuelActivityGuid = fuelActivityGuid;
    }

    public Date getFuelActivityDate() {
        return fuelActivityDate;
    }

    public void setFuelActivityDate(Date fuelActivityDate) {
        this.fuelActivityDate = fuelActivityDate;
    }

    public Long getFuelActivityOdometer() {
        return fuelActivityOdometer;
    }

    public void setFuelActivityOdometer(Long fuelActivityOdometer) {
        this.fuelActivityOdometer = fuelActivityOdometer;
    }

    public Double getFuelActivityGallons() {
        return fuelActivityGallons;
    }

    public void setFuelActivityGallons(Double fuelActivityGallons) {
        this.fuelActivityGallons = fuelActivityGallons;
    }

    public Double getFuelActivityCostPerGallon() {
        return fuelActivityCostPerGallon;
    }

    public void setFuelActivityCostPerGallon(Double fuelActivityCostPerGallon) {
        this.fuelActivityCostPerGallon = fuelActivityCostPerGallon;
    }

    public Double getFuelActivityTotalCost() {
        return fuelActivityTotalCost;
    }

    public void setFuelActivityTotalCost(Double fuelActivityTotalCost) {
        this.fuelActivityTotalCost = fuelActivityTotalCost;
    }

    public Double getFuelActivityMilesTraveled() {
        return fuelActivityMilesTraveled;
    }

    public void setFuelActivityMilesTraveled(Double fuelActivityMilesTraveled) {
        this.fuelActivityMilesTraveled = fuelActivityMilesTraveled;
    }

    public Double getFuelActivityMilesPerGallon() {
        return fuelActivityMilesPerGallon;
    }

    public void setFuelActivityMilesPerGallon(Double fuelActivityMilesPerGallon) {
        this.fuelActivityMilesPerGallon = fuelActivityMilesPerGallon;
    }

    public String getFuelActivityComments() {
        return fuelActivityComments;
    }

    public void setFuelActivityComments(String fuelActivityComments) {
        this.fuelActivityComments = fuelActivityComments;
    }

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
}

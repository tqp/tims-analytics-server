package com.timsanalytics.apps.main.beans;

import java.sql.Date;
import java.sql.Timestamp;

public class Fill {
    private String fillGuid;
    private String stationGuid;
    private Timestamp fillDateTime;
    private Double fillOdometer;
    private Double fillGallons;
    private Double fillCostPerGallon;
    private Double fillTotalCost;
    private Double fillMilesTraveled;
    private Double fillMilesPerGallon;
    private String fillComments;

    public String getFillGuid() {
        return fillGuid;
    }

    public void setFillGuid(String fillGuid) {
        this.fillGuid = fillGuid;
    }

    public String getStationGuid() {
        return stationGuid;
    }

    public void setStationGuid(String stationGuid) {
        this.stationGuid = stationGuid;
    }

    public Timestamp getFillDateTime() {
        return fillDateTime;
    }

    public void setFillDateTime(Timestamp fillDateTime) {
        this.fillDateTime = fillDateTime;
    }

    public Double getFillOdometer() {
        return fillOdometer;
    }

    public void setFillOdometer(Double fillOdometer) {
        this.fillOdometer = fillOdometer;
    }

    public Double getFillGallons() {
        return fillGallons;
    }

    public void setFillGallons(Double fillGallons) {
        this.fillGallons = fillGallons;
    }

    public Double getFillCostPerGallon() {
        return fillCostPerGallon;
    }

    public void setFillCostPerGallon(Double fillCostPerGallon) {
        this.fillCostPerGallon = fillCostPerGallon;
    }

    public Double getFillTotalCost() {
        return fillTotalCost;
    }

    public void setFillTotalCost(Double fillTotalCost) {
        this.fillTotalCost = fillTotalCost;
    }

    public Double getFillMilesTraveled() {
        return fillMilesTraveled;
    }

    public void setFillMilesTraveled(Double fillMilesTraveled) {
        this.fillMilesTraveled = fillMilesTraveled;
    }

    public Double getFillMilesPerGallon() {
        return fillMilesPerGallon;
    }

    public void setFillMilesPerGallon(Double fillMilesPerGallon) {
        this.fillMilesPerGallon = fillMilesPerGallon;
    }

    public String getFillComments() {
        return fillComments;
    }

    public void setFillComments(String fillComments) {
        this.fillComments = fillComments;
    }
}

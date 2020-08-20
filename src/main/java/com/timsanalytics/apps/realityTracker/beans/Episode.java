package com.timsanalytics.apps.realityTracker.beans;

import java.sql.Date;

public class Episode {
    // Series
    private String seriesGuid;
    private String seriesName;
    private String seriesAbbreviation;
    // Season
    private String seasonGuid;
    private String seasonName;
    private String seasonAbbreviation;
    // Episode
    private String episodeGuid;
    private String episodeName;
    // Metadata
    private String status;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;

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

    public String getSeriesAbbreviation() {
        return seriesAbbreviation;
    }

    public void setSeriesAbbreviation(String seriesAbbreviation) {
        this.seriesAbbreviation = seriesAbbreviation;
    }

    public String getSeasonGuid() {
        return seasonGuid;
    }

    public void setSeasonGuid(String seasonGuid) {
        this.seasonGuid = seasonGuid;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getSeasonAbbreviation() {
        return seasonAbbreviation;
    }

    public void setSeasonAbbreviation(String seasonAbbreviation) {
        this.seasonAbbreviation = seasonAbbreviation;
    }

    public String getEpisodeGuid() {
        return episodeGuid;
    }

    public void setEpisodeGuid(String episodeGuid) {
        this.episodeGuid = episodeGuid;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}

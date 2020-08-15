package com.timsanalytics.main.realityTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    private String guid;
    private String seasonGuid;
    private String contestantGuid;
    private String occupation;
    private Integer ageOnEntry;
    // Joined Fields
    private String seriesGuid;
    private String seriesName;
    private String seriesAbbreviation;
    private String seasonName;
    private String seasonAbbreviation;
    private String contestantLastName;
    private String contestantFirstName;
    // Metadata
    private String status;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSeasonGuid() {
        return seasonGuid;
    }

    public void setSeasonGuid(String seasonGuid) {
        this.seasonGuid = seasonGuid;
    }

    public String getContestantGuid() {
        return contestantGuid;
    }

    public void setContestantGuid(String contestantGuid) {
        this.contestantGuid = contestantGuid;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getAgeOnEntry() {
        return ageOnEntry;
    }

    public void setAgeOnEntry(Integer ageOnEntry) {
        this.ageOnEntry = ageOnEntry;
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

    public String getSeriesAbbreviation() {
        return seriesAbbreviation;
    }

    public void setSeriesAbbreviation(String seriesAbbreviation) {
        this.seriesAbbreviation = seriesAbbreviation;
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

    public String getContestantLastName() {
        return contestantLastName;
    }

    public void setContestantLastName(String contestantLastName) {
        this.contestantLastName = contestantLastName;
    }

    public String getContestantFirstName() {
        return contestantFirstName;
    }

    public void setContestantFirstName(String contestantFirstName) {
        this.contestantFirstName = contestantFirstName;
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

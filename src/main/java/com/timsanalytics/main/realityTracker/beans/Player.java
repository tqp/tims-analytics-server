package com.timsanalytics.main.realityTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    private String playerGuid;
    private String playerOccupation;
    private Integer playerAgeOnEntry;
    // Joined Fields
    private String seriesGuid;
    private String seriesName;
    private String seriesAbbreviation;
    private String seasonGuid;
    private String seasonName;
    private String seasonAbbreviation;
    private String contestantGuid;
    private String contestantLastName;
    private String contestantFirstName;
    // Metadata
    private String status;
    private String createdOn;
    private String createdBy;
    private String updatedOn;
    private String updatedBy;

    public String getPlayerGuid() {
        return playerGuid;
    }

    public void setPlayerGuid(String playerGuid) {
        this.playerGuid = playerGuid;
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

    public String getPlayerOccupation() {
        return playerOccupation;
    }

    public void setPlayerOccupation(String playerOccupation) {
        this.playerOccupation = playerOccupation;
    }

    public Integer getPlayerAgeOnEntry() {
        return playerAgeOnEntry;
    }

    public void setPlayerAgeOnEntry(Integer playerAgeOnEntry) {
        this.playerAgeOnEntry = playerAgeOnEntry;
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

package com.timsanalytics.main.realityTracker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Player {
    private String guid;
    private String seasonGuid;
    private String contestantGuid;
    // Joined Fields
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

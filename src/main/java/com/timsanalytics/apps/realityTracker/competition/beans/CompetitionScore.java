package com.timsanalytics.apps.realityTracker.competition.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompetitionScore {
    private String teamKey;
    private String userKey;
    private Integer roundNumber;
    private Integer pickPosition;
    private Integer score;
    private CompetitionPickResult.Status status;

    public CompetitionScore(String teamKey, String userKey, Integer roundNumber, Integer pickPosition, Integer score, CompetitionPickResult.Status status) {
        this.teamKey = teamKey;
        this.userKey = userKey;
        this.roundNumber = roundNumber;
        this.pickPosition = pickPosition;
        this.score = score;
        this.status = status;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Integer getPickPosition() {
        return pickPosition;
    }

    public void setPickPosition(Integer pickPosition) {
        this.pickPosition = pickPosition;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public CompetitionPickResult.Status getStatus() {
        return status;
    }

    public void setStatus(CompetitionPickResult.Status status) {
        this.status = status;
    }
}

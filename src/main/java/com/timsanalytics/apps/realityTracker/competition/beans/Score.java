package com.timsanalytics.apps.realityTracker.competition.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Score {
    private String teamKey;
    private String userKey;
    private Integer roundNumber;
    private Integer pickPosition;
    private Integer score;
    private PickResult.Status status;

    public Score(String teamKey, String userKey, Integer roundNumber, Integer pickPosition, Integer score, PickResult.Status status) {
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

    public PickResult.Status getStatus() {
        return status;
    }

    public void setStatus(PickResult.Status status) {
        this.status = status;
    }
}

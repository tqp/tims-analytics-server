package com.timsanalytics.apps.realityCompetition.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Score {
    private String teamKey;
    private String userKey;
    private Integer roundNumber;
    private Integer position;
    private String contestantKey;
    private Double score;
    private PickResult.Status status;

    public Score(String teamKey, String userKey, Integer roundNumber, Integer position, String contestantKey, Double score, PickResult.Status status) {
        this.teamKey = teamKey;
        this.userKey = userKey;
        this.roundNumber = roundNumber;
        this.position = position;
        this.contestantKey = contestantKey;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public PickResult.Status getStatus() {
        return status;
    }

    public void setStatus(PickResult.Status status) {
        this.status = status;
    }
}

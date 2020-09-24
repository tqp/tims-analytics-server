package com.timsanalytics.apps.realityCompetition.beans;

public class Chart {
    private String contestantKey;
    private Integer roundNumber;
    private Integer position;
    private PickResult.Status status;

    public Chart(String contestantKey, Integer roundNumber, Integer position, PickResult.Status status) {
        this.contestantKey = contestantKey;
        this.roundNumber = roundNumber;
        this.position = position;
        this.status = status;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
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

    public PickResult.Status getStatus() {
        return status;
    }

    public void setStatus(PickResult.Status status) {
        this.status = status;
    }
}

package com.timsanalytics.main.realityTracker.competition.beans;

public class Round {
    private Integer roundNumber;
    private Integer roundCutoffCount;
    private Integer roundPoints;

    public Round(Integer roundNumber, Integer roundCutoffCount, Integer roundPoints) {
        this.roundNumber = roundNumber;
        this.roundCutoffCount = roundCutoffCount;
        this.roundPoints = roundPoints;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Integer getRoundCutoffCount() {
        return roundCutoffCount;
    }

    public void setRoundCutoffCount(Integer roundCutoffCount) {
        this.roundCutoffCount = roundCutoffCount;
    }

    public Integer getRoundPoints() {
        return roundPoints;
    }

    public void setRoundPoints(Integer roundPoints) {
        this.roundPoints = roundPoints;
    }
}

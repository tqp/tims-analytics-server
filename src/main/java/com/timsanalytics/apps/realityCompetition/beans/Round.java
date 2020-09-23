package com.timsanalytics.apps.realityCompetition.beans;

public class Round {
    private Integer roundNumber;
    private Integer roundCutoffCount;
    private Double roundPoints;

    public Round(Integer roundNumber, Integer roundCutoffCount, Double roundPoints) {
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

    public Double getRoundPoints() {
        return roundPoints;
    }

    public void setRoundPoints(Double roundPoints) {
        this.roundPoints = roundPoints;
    }
}

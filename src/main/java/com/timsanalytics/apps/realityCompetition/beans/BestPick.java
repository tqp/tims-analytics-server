package com.timsanalytics.apps.realityCompetition.beans;

public class BestPick {
    private String contestantKey;
    private Double myScore;
    private Double theirAverageScore;
    private Double differential;

    public BestPick(String contestantKey, Double myScore, Double theirAverageScore, Double differential) {
        this.contestantKey = contestantKey;
        this.myScore = myScore;
        this.theirAverageScore = theirAverageScore;
        this.differential = differential;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public Double getMyScore() {
        return myScore;
    }

    public void setMyScore(Double myScore) {
        this.myScore = myScore;
    }

    public Double getTheirAverageScore() {
        return theirAverageScore;
    }

    public void setTheirAverageScore(Double theirAverageScore) {
        this.theirAverageScore = theirAverageScore;
    }

    public Double getPointDifferential() {
        return differential;
    }

    public void setDifferential(Double differential) {
        this.differential = differential;
    }
}

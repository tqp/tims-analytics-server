package com.timsanalytics.apps.realityTracker.competition.beans;

public class Result {
    private Integer roundNumber;
    private String contestantKey;
    private Integer callOutOrder;

    public Result(Integer roundNumber, String contestantKey, Integer callOutOrder) {
        this.roundNumber = roundNumber;
        this.contestantKey = contestantKey;
        this.callOutOrder = callOutOrder;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public Integer getCallOutOrder() {
        return callOutOrder;
    }

    public void setCallOutOrder(Integer callOutOrder) {
        this.callOutOrder = callOutOrder;
    }
}

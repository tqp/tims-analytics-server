package com.timsanalytics.apps.realityCompetition.beans;

public class Result {
    private Integer roundNumber;
    private Integer callOutOrder;
    private String contestantKey;

    public Result(Integer roundNumber, Integer callOutOrder, String contestantKey) {
        this.roundNumber = roundNumber;
        this.callOutOrder = callOutOrder;
        this.contestantKey = contestantKey;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Integer getCallOutOrder() {
        return callOutOrder;
    }

    public void setCallOutOrder(Integer callOutOrder) {
        this.callOutOrder = callOutOrder;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }
}

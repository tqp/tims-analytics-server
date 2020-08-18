package com.timsanalytics.apps.realityTracker.competition.beans;

public class BestPick {
    private String contestantKey;

    public BestPick(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }
}

package com.timsanalytics.apps.realityTracker.competition.beans;

public class CompetitionBestPick {
    private String contestantKey;

    public CompetitionBestPick(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }
}

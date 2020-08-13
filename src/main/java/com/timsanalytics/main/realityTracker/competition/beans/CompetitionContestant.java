package com.timsanalytics.main.realityTracker.competition.beans;

public class CompetitionContestant {
    private String contestantKey;
    private String contestantName;

    public CompetitionContestant(String contestantKey, String contestantName) {
        this.contestantKey = contestantKey;
        this.contestantName = contestantName;
    }

    public String getContestantKey() {
        return contestantKey;
    }

    public void setContestantKey(String contestantKey) {
        this.contestantKey = contestantKey;
    }

    public String getContestantName() {
        return contestantName;
    }

    public void setContestantName(String contestantName) {
        this.contestantName = contestantName;
    }
}

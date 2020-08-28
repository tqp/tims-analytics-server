package com.timsanalytics.apps.realityTracker.competition.beans;

public class CompetitionTeam {
    private String teamKey;
    private String teamName;

    public CompetitionTeam(String teamKey, String teamName) {
        this.teamKey = teamKey;
        this.teamName = teamName;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}

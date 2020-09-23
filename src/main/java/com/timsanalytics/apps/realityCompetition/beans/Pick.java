package com.timsanalytics.apps.realityCompetition.beans;

public class Pick {
    private String teamKey;
    private String userKey;
    private String contestantKey;
    private Integer roundNumber;
    private Integer position;

    public Pick(String teamKey, String userKey, Integer position, String contestantKey) {
        this.teamKey = teamKey;
        this.userKey = userKey;
        this.position = position;
        this.contestantKey = contestantKey;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(String teamKey) {
        this.teamKey = teamKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
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
}

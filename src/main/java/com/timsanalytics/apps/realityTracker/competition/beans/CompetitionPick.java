package com.timsanalytics.apps.realityTracker.competition.beans;

public class CompetitionPick {
    private String teamKey;
    private String userKey;
    private String contestantKey;
    private Integer pickPosition;

    public CompetitionPick(String teamKey, String userKey, Integer pickPosition, String contestantKey) {
        this.teamKey = teamKey;
        this.userKey = userKey;
        this.pickPosition = pickPosition;
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

    public Integer getPickPosition() {
        return pickPosition;
    }

    public void setPickPosition(Integer pickPosition) {
        this.pickPosition = pickPosition;
    }
}

package com.timsanalytics.apps.realityTracker.competition.beans;

public class Player {
    private String userKey;
    private String userName;

    public Player(String userKey, String userName) {
        this.userKey = userKey;
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

package com.timsanalytics.apps.realityCompetition.beans;

public class User {
    private String userKey;
    private String userName;

    public User(String userKey, String userName) {
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

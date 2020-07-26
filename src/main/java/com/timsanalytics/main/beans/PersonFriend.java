package com.timsanalytics.main.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonFriend {
    private String personFriendGuid;
    private String personGuid;
    private String friendGuid;
    private String status;

    public String getPersonFriendGuid() {
        return personFriendGuid;
    }

    public void setPersonFriendGuid(String personFriendGuid) {
        this.personFriendGuid = personFriendGuid;
    }

    public String getPersonGuid() {
        return personGuid;
    }

    public void setPersonGuid(String personGuid) {
        this.personGuid = personGuid;
    }

    public String getFriendGuid() {
        return friendGuid;
    }

    public void setFriendGuid(String friendGuid) {
        this.friendGuid = friendGuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.timsanalytics.main.realityTracker.competition.beans;

public class PickResult {
    private com.timsanalytics.main.realityTracker.competition.beans.Pick pick;
    private com.timsanalytics.main.realityTracker.competition.beans.Result result;
    private Integer roundNumber;
    private Status status;

    public enum Status {
        CORRECT, WRONG, NOT_PICKED, UNKNOWN, INVALID, PROJECTED;
    }

    public PickResult(com.timsanalytics.main.realityTracker.competition.beans.Pick pick, com.timsanalytics.main.realityTracker.competition.beans.Result result, Integer roundNumber, Status status) {
        this.pick = pick;
        this.result = result;
        this.roundNumber = roundNumber;
        this.status = status;
    }

    public com.timsanalytics.main.realityTracker.competition.beans.Pick getPick() {
        return pick;
    }

    public void setPick(com.timsanalytics.main.realityTracker.competition.beans.Pick pick) {
        this.pick = pick;
    }

    public com.timsanalytics.main.realityTracker.competition.beans.Result getResult() {
        return result;
    }

    public void setResult(com.timsanalytics.main.realityTracker.competition.beans.Result result) {
        this.result = result;
    }

    public Integer getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(Integer roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

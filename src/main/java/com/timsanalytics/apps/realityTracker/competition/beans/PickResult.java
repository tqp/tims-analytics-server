package com.timsanalytics.apps.realityTracker.competition.beans;

public class PickResult {
    private Pick pick;
    private Result result;
    private Integer roundNumber;
    private Status status;

    public enum Status {
        CORRECT, WRONG, NOT_PICKED, UNKNOWN, INVALID, PROJECTED;
    }

    public PickResult(Pick pick, Result result, Integer roundNumber, Status status) {
        this.pick = pick;
        this.result = result;
        this.roundNumber = roundNumber;
        this.status = status;
    }

    public Pick getPick() {
        return pick;
    }

    public void setPick(Pick pick) {
        this.pick = pick;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
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

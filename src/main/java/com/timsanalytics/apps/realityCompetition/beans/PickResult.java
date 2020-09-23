package com.timsanalytics.apps.realityCompetition.beans;

public class PickResult {
    private Pick pick;
    private Result result;
    private Integer roundNumber;
    private Integer position;
    private Status status;

    public enum Status {
        CORRECT, WRONG, OUT_OF_SCOPE, NOT_PICKED, UNKNOWN, INVALID, PROJECTED;
    }

    public PickResult(Pick pick, Result result, Integer roundNumber, Integer position, Status status) {
        this.pick = pick;
        this.result = result;
        this.roundNumber = roundNumber;
        this.position = position;
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

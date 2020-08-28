package com.timsanalytics.apps.realityTracker.competition.beans;

public class CompetitionPickResult {
    private CompetitionPick pick;
    private CompetitionResult result;
    private Integer roundNumber;
    private Status status;

    public enum Status {
        CORRECT, WRONG, NOT_PICKED, UNKNOWN, INVALID, PROJECTED;
    }

    public CompetitionPickResult(CompetitionPick pick, CompetitionResult result, Integer roundNumber, Status status) {
        this.pick = pick;
        this.result = result;
        this.roundNumber = roundNumber;
        this.status = status;
    }

    public CompetitionPick getPick() {
        return pick;
    }

    public void setPick(CompetitionPick pick) {
        this.pick = pick;
    }

    public CompetitionResult getResult() {
        return result;
    }

    public void setResult(CompetitionResult result) {
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

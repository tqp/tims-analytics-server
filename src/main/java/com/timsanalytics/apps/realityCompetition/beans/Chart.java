package com.timsanalytics.apps.realityCompetition.beans;

public class Chart {
    private Contestant contestant;
    private Integer round;
    private Integer position;
    private Double points;
    private PickResult.Status status;

    public Chart(Contestant contestant, Integer round, Integer position, Double points, PickResult.Status status) {
        this.contestant = contestant;
        this.round = round;
        this.position = position;
        this.points = points;
        this.status = status;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public PickResult.Status getStatus() {
        return status;
    }

    public void setStatus(PickResult.Status status) {
        this.status = status;
    }
}

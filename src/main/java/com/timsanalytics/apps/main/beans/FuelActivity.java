package com.timsanalytics.apps.main.beans;

public class FuelActivity {
    private Fill fill;
    private Station station;

    public FuelActivity(Fill fill, Station station) {
        this.fill = fill;
        this.station = station;
    }

    public Fill getFill() {
        return fill;
    }

    public void setFill(Fill fill) {
        this.fill = fill;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}

package pl.pm.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;

    String getState() {
        return state;
    }

    void setState(String state) {
        this.state = state;
    }

    String getCountry() {
        return country;
    }

    void setCountry(String country) {
        this.country = country;
    }

    int getLatestTotalCases() {
        return latestTotalCases;
    }

    void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }
}

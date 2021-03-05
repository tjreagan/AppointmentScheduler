package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Country {

    private int countryId;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Country() {

    }

    public Country(String country) {
        this.country = country;
        this.createdBy = "admin";
        this.lastUpdateBy = "admin";
    }

    public Country(String country, String createdBy, String lastUpdateBy) {
        this.country = country;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Country(int countryId, String country, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCountryID(int countryId) { this.countryId = countryId; }
    public int getCountryID() { return countryId; }
    public void setCountry(String country) { this.country = country; }
    public String getCountry() { return country; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getCreatedBy() { return createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public Timestamp  getLastUpdate() { return lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }
    public String getLastUpdateBy() { return lastUpdateBy; }
}

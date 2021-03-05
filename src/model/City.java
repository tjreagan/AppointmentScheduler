package model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class City {
    private int cityId;
    private String city;
    private int countryId;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public City() {

    }

    public City(String city, int countryId) {
        this.city = city;
        this.countryId = countryId;
        this.createdBy = "admin";
        this.lastUpdateBy = "admin";
    }

    public City(String city, int countryId, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public City(int cityId, String city, int countryId, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCityID(int cityId) { this.cityId = cityId; }
    public int getCityID() { return cityId; }
    public void setCity(String city) { this.city = city; }
    public String getCity() { return city; }
    public void setCountryID(int countryId) { this.countryId = countryId; }
    public int getCountryId() { return countryId; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getCreatedBy() { return createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }
    public String getLastUpdateBy() { return lastUpdateBy; }
}

package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Address {
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Address(){

    }

    public Address(String address, int cityId, String postalCode, String phone) {
        this.address = address;
        this.cityId = cityId;
        this.createdBy = "admin";
        this.lastUpdateBy = "admin";
        this.address2 = "blah";
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Address(String address, String address2, int cityId, String postalCode, String phone, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setAddressID(int addressId) { this.addressId = addressId; }
    public int getAddressID() { return addressId; }
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return address; }
    public void setAddress2(String address2) { this.address2 = address2; }
    public String getAddress2() { return address2; }
    public void setCityID(int cityId) { this.cityId = cityId; }
    public int getCityID() { return cityId; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getPostalCode() { return postalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPhone() { return phone; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getCreatedBy() { return createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy;}
    public String getLastUpdateBy() { return lastUpdateBy; }
}

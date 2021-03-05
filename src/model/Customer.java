package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Customer {
    private int customerId;
    private String customerName;
    private int addressId;
    private boolean active;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Customer(){

    }

    public Customer(String customerName, int addressId) {
        this.customerName = customerName;
        this.addressId = addressId;
        this.createdBy = "admin";
        this.lastUpdateBy = "admin";
        this.active = true;
    }

    public Customer(String customerName, int addressId, boolean active, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Customer(int customerId, String customerName, int addressId, boolean active, LocalDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public void setCustomerID(int customerId) { this.customerId = customerId; }
    public int getCustomerID() { return customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerName() { return customerName; }
    public void setAddressID(int addressId) { this.addressId = addressId; }
    public int getAddressID() { return addressId; }
    public void setActive(boolean active) { this.active = active; }
    public boolean getActive() { return active; }
    public void setCreateDate(LocalDateTime createDate) { this.createDate = createDate; }
    public LocalDateTime getCreateDate() { return createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getCreatedBy() { return createdBy; }
    public void setLastUpdate(Timestamp lastUpdate) { this.lastUpdate = lastUpdate; }
    public Timestamp getLastUpdate() { return lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }
    public String getLastUpdateBy() { return lastUpdateBy; }
}

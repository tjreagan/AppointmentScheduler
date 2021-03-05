package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableViewCustomers {

    private String customerName;
    private String address;
    private String phone;
    private String zip;
    private String country;
    private String city;
    private int customerId;
    private static ObservableList<TableViewCustomers> customerList = FXCollections.observableArrayList();

    public TableViewCustomers() {

    }

    public TableViewCustomers(int customerId, String customerName, String address, String phone, String zip, String city, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public static ObservableList<TableViewCustomers> populateCustomerList() {
        try {
            customerList.clear();
            PreparedStatement statement = DBConnection.conn.prepareStatement("SELECT customerId, customerName, address, phone, postalCode, city, country FROM customer, address, city, country WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                customerList.add(new TableViewCustomers(rs.getInt("customerId"),rs.getString("customerName"),rs.getString("address"),rs.getString("phone"), rs.getString("postalCode"),rs.getString("city"),rs.getString("country")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return customerList;
    }

    public static ObservableList<TableViewCustomers> deleteCustomer(TableViewCustomers customer) {
        customerList.remove(customer);
        return customerList;
    }

    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
}

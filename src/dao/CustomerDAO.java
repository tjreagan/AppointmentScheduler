package dao;

import com.mysql.jdbc.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAO {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static Customer selectedCustomer = new Customer();

    public static void addCustomer(Customer customer) {
        try {
            PreparedStatement query = (PreparedStatement) DBConnection.conn.prepareStatement("SELECT * FROM customer WHERE customerName=?");
            query.setString(1,customer.getCustomerName());
            ResultSet rs = query.executeQuery();
            if(!rs.next()) {
                PreparedStatement statement = (PreparedStatement) DBConnection.conn.prepareStatement("INSERT INTO customer (customerName,addressId,active,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?,?,?,NOW(),?,NOW(),?)");
                statement.setString(1,customer.getCustomerName());
                statement.setInt(2,customer.getAddressID());
                statement.setBoolean(3,customer.getActive());
                statement.setString(4,customer.getCreatedBy());
                statement.setString(5,customer.getLastUpdateBy());
                statement.executeUpdate();
                rs = query.executeQuery();
                customer.setCustomerID(rs.getInt("customerId"));
                customer.setCreateDate((LocalDateTime) rs.getObject("createDate"));
                customer.setLastUpdate(rs.getTimestamp("lastUpdate"));
                allCustomers.add(customer);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
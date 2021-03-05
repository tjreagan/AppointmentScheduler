package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Address;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AddressDAO {

    private static ObservableList<Address> allAddresses = FXCollections.observableArrayList();

    public static void addAddress(Address address) {
        try {
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM address WHERE address = ?");
            query.setString(1,address.getAddress());
            ResultSet rs = query.executeQuery();
            if(!rs.next()) {
                PreparedStatement statement = DBConnection.conn.prepareStatement("INSERT INTO address (address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?,?,?,?,?,NOW(),?,NOW(),?)");
                statement.setString(1,address.getAddress());
                statement.setString(2,address.getAddress2());
                statement.setInt(3,address.getCityID());
                statement.setString(4,address.getPostalCode());
                statement.setString(5,address.getPhone());
                statement.setString(6,address.getCreatedBy());
                statement.setString(7,address.getLastUpdateBy());
                statement.executeUpdate();
                rs = query.executeQuery();
                address.setAddressID(rs.getInt("addressId"));
                address.setCreateDate((LocalDateTime) rs.getObject("createDate"));
                address.setLastUpdate(rs.getTimestamp("lastUpdate"));
                allAddresses.add(address);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

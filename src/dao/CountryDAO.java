package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CountryDAO {

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    public static void addCountry(Country country) {
        try {
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM country WHERE country = ?");
            query.setString(1, country.getCountry());
            ResultSet rs = query.executeQuery();
            if (!rs.next()) {
                PreparedStatement statement = DBConnection.conn.prepareStatement("INSERT INTO country (country, createDate, createdBy, lastUpdate,lastUpdateBy) VALUES (?,NOW(),?,NOW(),?)");
                statement.setString(1, country.getCountry());
                statement.setString(2, country.getCreatedBy());
                statement.setString(3, country.getLastUpdateBy());
                statement.executeUpdate();
                rs = query.executeQuery();
                country.setCountryID(rs.getInt("countryId"));
                country.setCreateDate((LocalDateTime) rs.getObject("createDate"));
                country.setLastUpdate(rs.getTimestamp("lastUpdate"));
                allCountries.add(country);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

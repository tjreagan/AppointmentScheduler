package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.City;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CityDAO {

    private static ObservableList<City> allCities = FXCollections.observableArrayList();

    public static void addCity(City city) {
        try {
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM city WHERE city = ?");
            query.setString(1, city.getCity());
            ResultSet rs = query.executeQuery();
            if (!rs.next()) {
                PreparedStatement statement = DBConnection.conn.prepareStatement("INSERT INTO city (city,countryId,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?,?,NOW(),?,NOW(),?)");
                statement.setString(1, city.getCity());
                statement.setInt(2, city.getCountryId());
                statement.setString(3, city.getCreatedBy());
                statement.setString(4, city.getLastUpdateBy());
                statement.executeUpdate();
                rs = query.executeQuery();
                city.setCityID(rs.getInt("cityId"));
                city.setCreateDate((LocalDateTime) rs.getObject("createDate"));
                city.setLastUpdate(rs.getTimestamp("lastUpdate"));
                allCities.add(city);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

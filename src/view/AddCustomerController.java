package view;

import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import utils.DBConnection;
import dao.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class AddCustomerController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    //Use lambda here to reduce clutter with repetitive alert popup code.
    private AlertPopup alert = (title, content) -> {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    };

    //Similarly use lambda for changing scenes. Only the fxml file changes, so I turned the code into a lambda function.
    private SceneChanger changeScene = (fxml, event) -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        changeScene.changeScene("MainView.fxml",event);
    }

    @FXML
    public void save(ActionEvent event) throws IOException {
        try {
            String customerName = nameTextField.getText();
            String address = addressTextField.getText();
            String city = cityTextField.getText();
            String phone = phoneTextField.getText();
            String zip = zipTextField.getText();
            String country = countryTextField.getText();
            LocalDateTime localDateTime = LocalDateTime.now();
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp timestamp = new java.sql.Timestamp(now.getTime());

            if(customerName.equals("") || address.equals("") || city.equals("") || phone.equals("") || zip.equals("") || country.equals("")) {
                alert.alert("Error!","All fields must contain a value!");

            } else {

                Country newCountry = new Country(country);
                CountryDAO.addCountry(newCountry);

                PreparedStatement getCountryId = (PreparedStatement) DBConnection.conn.prepareStatement("SELECT countryId FROM country WHERE country = ?");
                getCountryId.setString(1, country);
                ResultSet rsCountryID = getCountryId.executeQuery();
                rsCountryID.next();
                int countryId = rsCountryID.getInt("countryId");

                City newCity = new City(city, countryId);
                CityDAO.addCity(newCity);

                PreparedStatement getCityId = (PreparedStatement) DBConnection.conn.prepareStatement("SELECT cityId FROM city WHERE city = ?");
                getCityId.setString(1, city);
                ResultSet rsCityID = getCityId.executeQuery();
                rsCityID.next();
                int cityId = rsCityID.getInt("cityId");

                Address newAddress = new Address(address, cityId, zip, phone);
                AddressDAO.addAddress(newAddress);

                PreparedStatement getAddressId = (PreparedStatement) DBConnection.conn.prepareStatement("SELECT addressId FROM address WHERE address = ?");
                getAddressId.setString(1, address);
                ResultSet rsAddressID = getAddressId.executeQuery();
                rsAddressID.next();
                int addressId = rsAddressID.getInt("addressId");

                Customer newCustomer = new Customer(customerName, addressId);
                CustomerDAO.addCustomer(newCustomer);

                changeScene.changeScene("MainView.fxml",event);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
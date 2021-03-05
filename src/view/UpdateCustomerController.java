package view;

import com.sun.tools.javac.Main;
import dao.AddressDAO;
import dao.CityDAO;
import dao.CountryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCustomerController implements Initializable {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private TableViewCustomers customerToUpdate = MainViewController.customerToUpdate;

    //Used lambda to reduce clutter of repetitive alert popup code.
    private AlertPopup alert = (title, content) -> {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    };

    //Used lambda to reduce clutter of repetitive scene changing code.
    private SceneChanger changeScene = (fxml, event) -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    public void save(ActionEvent event) throws IOException {

        String customerName = nameTextField.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        String phone = phoneTextField.getText();
        String zip = zipTextField.getText();
        String country = countryTextField.getText();
        int countryId;
        int cityId;
        int addressId;

        if(customerName.equals("") || address.equals("") || city.equals("") || phone.equals("") || zip.equals("") || country.equals("")) {
            alert.alert("Error!","All fields must contain a value!");
        } else {
            try {
                PreparedStatement customerStatement = DBConnection.conn.prepareStatement("UPDATE customer SET customerName=? WHERE customerName=?");
                customerStatement.setString(1,customerName);
                customerStatement.setString(2,customerToUpdate.getCustomerName());
                customerStatement.executeUpdate();

                PreparedStatement addressStatement = DBConnection.conn.prepareStatement("UPDATE address SET address=?,phone=?,postalCode=? WHERE address=?");
                addressStatement.setString(1,address);
                addressStatement.setString(2,phone);
                addressStatement.setString(3,zip);
                addressStatement.setString(4,customerToUpdate.getAddress());
                addressStatement.executeUpdate();

                PreparedStatement cityStatement = DBConnection.conn.prepareStatement("UPDATE city SET city=? WHERE city=?");
                cityStatement.setString(1,city);
                cityStatement.setString(2,customerToUpdate.getCity());
                cityStatement.executeUpdate();

                PreparedStatement countryStatement = DBConnection.conn.prepareStatement("UPDATE country SET country=? WHERE country=?");
                countryStatement.setString(1,country);
                countryStatement.setString(2,customerToUpdate.getCountry());
                countryStatement.executeUpdate();

            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }

        }

        changeScene.changeScene("MainView.fxml",event);
    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        changeScene.changeScene("MainView.fxml",event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameTextField.setText(customerToUpdate.getCustomerName());
        addressTextField.setText(customerToUpdate.getAddress());
        cityTextField.setText(customerToUpdate.getCity());
        countryTextField.setText(customerToUpdate.getCountry());
        zipTextField.setText(customerToUpdate.getZip());
        phoneTextField.setText(customerToUpdate.getPhone());
    }
}
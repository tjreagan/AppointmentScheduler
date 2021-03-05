package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Reports;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ReportsPageController implements Initializable {

    @FXML
    private TableView<Reports> reportOneTable;

    @FXML
    private TableColumn<?, ?> monthColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> countColumn;

    @FXML
    private TableView<Reports> reportTwoTable;

    @FXML
    private TableColumn<?, ?> consultantColumn;

    @FXML
    private TableColumn<?, ?> appointmentsColumn;

    @FXML
    private TableView<Reports> reportThreeTable;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> addressesColumn;

    @FXML
    private Button backButton;

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Reports> report1 = FXCollections.observableArrayList();
        ObservableList<Reports> report2 = FXCollections.observableArrayList();
        ObservableList<Reports> report3 = FXCollections.observableArrayList();

        try {
            PreparedStatement report1Statement = DBConnection.conn.prepareStatement("SELECT type, monthname(start) as 'Month', COUNT(*) FROM appointment GROUP BY MONTH(start), type");
            ResultSet r1rs = report1Statement.executeQuery();
            while(r1rs.next()) {
                Reports reports1 = new Reports(r1rs.getString("Month"), r1rs.getInt("COUNT(*)"), r1rs.getString("type"));
                report1.add(reports1);
            }

            PreparedStatement report2Statement = DBConnection.conn.prepareStatement("SELECT user.userName, appointment.start FROM appointment, user WHERE user.userId = appointment.userId ORDER BY userName, start");
            ResultSet r2rs = report2Statement.executeQuery();
            while(r2rs.next()) {
                Timestamp timestamp = Timestamp.valueOf(r2rs.getString("start"));
                Reports reports2 = new Reports((r2rs.getString("userName")),timestamp);
                report2.add(reports2);
            }

            PreparedStatement report3Statement = DBConnection.conn.prepareStatement("SELECT city.city, address.cityId, COUNT(*) FROM city, address WHERE address.cityId = city.cityId GROUP BY city");
            ResultSet r3rs = report3Statement.executeQuery();
            while(r3rs.next()) {
                Reports reports3 = new Reports(r3rs.getString("city"),r3rs.getInt("COUNT(*)"));
                report3.add(reports3);
            }

            monthColumn.setCellValueFactory(new PropertyValueFactory<>("string"));
            countColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            reportOneTable.setItems(report1);

            consultantColumn.setCellValueFactory(new PropertyValueFactory<>("string"));
            appointmentsColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            reportTwoTable.setItems(report2);

            cityColumn.setCellValueFactory(new PropertyValueFactory<>("string"));
            addressesColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
            reportThreeTable.setItems(report3);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
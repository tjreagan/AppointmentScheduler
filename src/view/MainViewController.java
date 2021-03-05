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
import model.Appointment;
import model.SceneChanger;
import model.TableViewCustomers;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import view.LoginController;

public class MainViewController implements Initializable {

    @FXML
    private TableView<TableViewCustomers> customerTable;

    @FXML
    private TableColumn<TableViewCustomers, String> customerNameColumn;

    @FXML
    private TableColumn<TableViewCustomers, String> customerAddressColumn;

    @FXML
    private TableColumn<TableViewCustomers, String> customerPhoneColumn;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button updateCustomerButton;

    @FXML
    private Button deleteCustomerButton;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<?, ?> appointmentIDColumn;

    @FXML
    private TableColumn<?, ?> consultantColumn;

    @FXML
    private TableColumn<?, ?> customerColumn;

    @FXML
    private TableColumn<?, ?> startColumn;

    @FXML
    private TableColumn<?, ?> endColumn;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private TableColumn<?, ?> monthColumn;

    @FXML
    private TableColumn<?, ?> weekColumn;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private Button reportsButton;

    private TableView table;
    private ObservableList<TableViewCustomers> customers = FXCollections.observableArrayList();
    public static TableViewCustomers customerToUpdate = new TableViewCustomers();
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    public static Appointment appointmentToUpdate = new Appointment();

    //Use lambda for changing scenes to reduce clutter. Only the fxml file changes, so I turned the code into a lambda function.
    private SceneChanger changeScene = (fxml, event) -> {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    };

    @FXML
    public void addCustomer(ActionEvent event) throws IOException {
        changeScene.changeScene("AddCustomer.fxml",event);
    }

    @FXML
    public void updateCustomer(ActionEvent event) throws IOException {
        customerToUpdate = customerTable.getSelectionModel().getSelectedItem();

        changeScene.changeScene("UpdateCustomer.fxml",event);
    }

    @FXML
    public void addAppointment(ActionEvent event) throws IOException {
        changeScene.changeScene("AddAppointment.fxml",event);
    }

    @FXML
    public void updateAppointment(ActionEvent event) throws IOException {
        appointmentToUpdate = appointmentTable.getSelectionModel().getSelectedItem();

        changeScene.changeScene("UpdateAppointment.fxml",event);
    }

    @FXML
    public void deleteAppointmentFromList(ActionEvent event) throws IOException {
        try {
            Appointment deleteThisAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            PreparedStatement statement = DBConnection.conn.prepareStatement("DELETE FROM appointment WHERE appointmentId=?");
            statement.setInt(1,deleteThisAppointment.getAppointmentId());
            statement.executeUpdate();
            appointments.remove(deleteThisAppointment);
            appointments = Appointment.populateAppointmentList();
            appointmentTable.setItems(appointments);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void deleteCustomerFromList(ActionEvent event) throws IOException {
        try {
            TableViewCustomers deleteThisCustomer = customerTable.getSelectionModel().getSelectedItem();
            PreparedStatement statement = DBConnection.conn.prepareStatement("DELETE FROM customer WHERE customerId=?");
            statement.setInt(1, deleteThisCustomer.getCustomerId());
            statement.executeUpdate();
            customers.remove(deleteThisCustomer);
            customers = TableViewCustomers.populateCustomerList();
            customerTable.setItems(customers);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void reports(ActionEvent event) throws IOException {
        changeScene.changeScene("ReportsPage.fxml",event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customers = TableViewCustomers.populateCustomerList();
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTable.setItems(customers);

        appointments = Appointment.populateAppointmentList();
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        consultantColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("displayAppointmentStartTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("displayAppointmentEndTime"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        weekColumn.setCellValueFactory(new PropertyValueFactory<>("week"));
        appointmentTable.setItems(appointments);
    }
}
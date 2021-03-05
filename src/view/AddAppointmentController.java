package view;

import dao.AppointmentDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> startTimeComboBox;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private TableView<TableViewCustomers> customersTable;

    @FXML
    private TableColumn<?, ?> customerIDColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableView<User> consultantTable;

    @FXML
    private TableColumn<?, ?> consultantIDColumn;

    @FXML
    private TableColumn<?, ?> consultantNameColumn;

    @FXML
    private TextField customerIDTextField;

    @FXML
    private TextField consultantIDTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private DatePicker dateSelector;

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
    public void save(ActionEvent event) throws IOException {

        if (customerIDTextField.getText().isEmpty() || consultantIDTextField.getText().isEmpty() || typeTextField.getText().isEmpty()) {
            alert.alert("Error!","All fields must contain a value!");
        } else if (Integer.parseInt(startTimeComboBox.getValue()) >= Integer.parseInt(endTimeComboBox.getValue())) {
            alert.alert("Error!","Start time must be earlier than end time!");
        } else {
            try {
                String custIdString = customerIDTextField.getText();
                String consIdString = consultantIDTextField.getText();
                int customerId = Integer.parseInt(customerIDTextField.getText());
                int consultantId = Integer.parseInt(consultantIDTextField.getText());
                int startHour = Integer.parseInt(startTimeComboBox.getValue());
                int endHour = Integer.parseInt(endTimeComboBox.getValue());
                String startHourString = startTimeComboBox.getValue();
                String endHourString = endTimeComboBox.getValue();
                String type = typeTextField.getText();
                String date = dateSelector.getValue().toString();
                String fullStartDateTime = String.format("%s %s:00",date,startHourString);
                String fullEndDateTime = String.format("%s %s:00",date,endHourString);

                ZoneId zoneId = ZoneId.systemDefault();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime startDateTime = LocalDateTime.parse(fullStartDateTime,formatter);
                LocalDateTime endDateTime = LocalDateTime.parse(fullEndDateTime,formatter);
                ZonedDateTime zonedDateTimeStart = startDateTime.atZone(zoneId);
                ZonedDateTime zonedDateTimeEnd = endDateTime.atZone(zoneId);
                LocalDateTime zonedStart = zonedDateTimeStart.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
                LocalDateTime zonedEnd = zonedDateTimeEnd.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
                Timestamp sdt = Timestamp.valueOf(zonedStart);
                Timestamp edt = Timestamp.valueOf(zonedEnd);

                PreparedStatement checkForOverlap = DBConnection.conn.prepareStatement("SELECT * FROM appointment WHERE start = ?");
                checkForOverlap.setTimestamp(1,sdt);
                ResultSet rs = checkForOverlap.executeQuery();

                if(rs.isBeforeFirst() && rs.next() && rs.isFirst() && rs.isLast()) {
                    alert.alert("Error!","Appointment times cannot overlap!");
                } else {
                    Appointment newAppointment = new Appointment(customerId, consultantId, type, sdt, edt);
                    AppointmentDAO.addAppointment(newAppointment);
                    changeScene.changeScene("MainView.fxml",event);
                }

            } catch (NumberFormatException | SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    public void cancel(ActionEvent event) throws IOException {
        changeScene.changeScene("MainView.fxml",event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTimeComboBox.getItems().addAll("09","10","11","12","13","14","15","16","17");
        endTimeComboBox.getItems().addAll("09","10","11","12","13","14","15","16","17");

        ObservableList<TableViewCustomers> customers = TableViewCustomers.populateCustomerList();
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customersTable.setItems(customers);

        ObservableList<User> consultants = User.populateUserList();
        consultantIDColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        consultantNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        consultantTable.setItems(consultants);
    }
}
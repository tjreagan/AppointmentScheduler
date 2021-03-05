package view;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.AlertPopup;
import model.SceneChanger;
import model.User;
import utils.DBConnection;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label schedulerLabel;

    private String error;
    private String invalidLogin;

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
    public void login(javafx.event.ActionEvent event) throws IOException, SQLException {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            boolean login = UserDAO.userLogin(username, password);
            if (login) {
                String loginLine = LocalDateTime.now() + " : " + username + " logged in. \n";
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("userlog.txt",true));
                bufferedWriter.append(loginLine);
                bufferedWriter.close();

                int currentUserId = 0;
                PreparedStatement idstatement = DBConnection.conn.prepareStatement("SELECT * FROM user WHERE userName = ?");
                idstatement.setString(1,username);
                ResultSet idrs = idstatement.executeQuery();
                while (idrs.next()) {
                    currentUserId = idrs.getInt("userId");
                }

                LocalDateTime currentTime = LocalDateTime.now();
                ZoneId zoneId = ZoneId.systemDefault();
                ZonedDateTime zonedDateTime = currentTime.atZone(zoneId);
                LocalDateTime localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
                LocalDateTime fifteenMinutes = localDateTime.plusMinutes(15);

                PreparedStatement statement = DBConnection.conn.prepareStatement("SELECT * FROM appointment WHERE start BETWEEN ? AND ? AND userId=?");
                statement.setTimestamp(1, Timestamp.valueOf(localDateTime));
                statement.setTimestamp(2,Timestamp.valueOf(fifteenMinutes));
                statement.setInt(3,currentUserId);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    alert.alert("Appointment Soon!","There is an appointment under 15 minutes from now!");
                }
                changeScene.changeScene("MainView.fxml",event);
            } else {
                alert.alert(error,invalidLogin);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Locale locale = Locale.getDefault();
        rb = ResourceBundle.getBundle("languages", locale);
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
        schedulerLabel.setText(rb.getString("title"));
        loginButton.setText(rb.getString("login"));
        error = rb.getString("error");
        invalidLogin = rb.getString("invalidLogin");
    }
}
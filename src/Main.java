import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.SQLException;
import java.time.ZoneId;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
        Parent root = loader.load();
        Scene primaryScene = new Scene(root);
        stage.setScene(primaryScene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
        ZoneId.getAvailableZoneIds().forEach(System.out::println);
        launch(args);
        DBConnection.closeConnection();
    }
}

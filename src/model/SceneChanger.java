package model;

import javafx.event.ActionEvent;

import java.io.IOException;

public interface SceneChanger {
    //Used lambda expressions using this interface to reduce the clutter of repeating scene change code
    void changeScene(String fxml, ActionEvent event) throws IOException;
}

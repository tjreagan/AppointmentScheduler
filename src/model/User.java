package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import utils.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private String username;
    private int userId;
    private static ObservableList<User> userList = FXCollections.observableArrayList();

    public User(){

    }

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static ObservableList<User> populateUserList() {
        try {
            userList.clear();
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM user");
            ResultSet rs = query.executeQuery();
            while(rs.next()) {
                User user = new User(rs.getInt("userId"),rs.getString("userName"));
                userList.add(user);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() {
        return username;
    }

    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }
}

package dao;

import com.mysql.jdbc.PreparedStatement;
import model.User;
import utils.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static User activeUser = new User();

    public UserDAO() throws SQLException {

    }

    public static boolean userLogin(String username, String password) throws SQLException {
        PreparedStatement statement = (PreparedStatement) DBConnection.conn.prepareStatement("SELECT userName, password FROM user WHERE userName = ? AND password = ?");
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();

        return rs.next();
    }
}

package utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//3.227.166.251/U06zPL";

    // JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    // Driver and Connection Interface Reference
    private static final String MySQLJDBCDriver = "com.mysql.jdbc.Driver";
    public static Connection conn = null;

    // Username and Password
    private static final String username = "U06zPL";
    private static final String password = "53688916512";

    public static Connection startConnection() {
        try {
            Class.forName(MySQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.print("Connection Successful");
        }
        catch(ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection Closed");
    }
}

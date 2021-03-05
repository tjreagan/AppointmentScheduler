package utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import java.sql.SQLException;

public class DBQuery {

    // Statement Reference
    private static PreparedStatement statement;

    // Create Statement Object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = (PreparedStatement) conn.prepareStatement(sqlStatement);
    }

    // Return Statement Object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }
}

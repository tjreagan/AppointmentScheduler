package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Address;
import model.Appointment;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDAO {
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void addAppointment(Appointment appointment) {
        try {
            PreparedStatement query = DBConnection.conn.prepareStatement("SELECT * FROM appointment WHERE appointmentId=?");
            query.setInt(1,appointment.getAppointmentId());
            ResultSet rs = query.executeQuery();
            if(!rs.next()) {
                PreparedStatement statement = DBConnection.conn.prepareStatement("INSERT INTO appointment (customerId,userId,title,description,location,contact,type,url,start,end,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES (?,?,?,?,?,?,?,?,?,?,NOW(),?,NOW(),?)");
                statement.setInt(1,appointment.getCustomerId());
                statement.setInt(2,appointment.getUserId());
                statement.setString(3,appointment.getTitle());
                statement.setString(4,appointment.getDescription());
                statement.setString(5,appointment.getLocation());
                statement.setString(6,appointment.getContact());
                statement.setString(7,appointment.getType());
                statement.setString(8,appointment.getUrl());
                statement.setTimestamp(9,appointment.getStart());
                statement.setTimestamp(10,appointment.getEnd());
                statement.setString(11,"admin");
                statement.setString(12,"admin");
                statement.executeUpdate();
                allAppointments.add(appointment);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateAppointment(Appointment appointment) {
        try {
            PreparedStatement statement = DBConnection.conn.prepareStatement("UPDATE appointment SET customerId=?,userId=?,type=?,start=?,end=? WHERE appointmentId=?");
            statement.setInt(1,appointment.getCustomerId());
            statement.setInt(2,appointment.getUserId());
            statement.setString(3,appointment.getType());
            statement.setTimestamp(4,appointment.getStart());
            statement.setTimestamp(5,appointment.getEnd());
            statement.setInt(6,appointment.getAppointmentId());
            statement.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

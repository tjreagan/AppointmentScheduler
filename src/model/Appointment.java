package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Appointment {

    private int appointmentId;
    private int customerId;
    private int userId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String url;
    private Timestamp start;
    private Timestamp end;
    private LocalDateTime createDate;
    private Timestamp lastUpdate;
    private String lastUpdateBy;
    private String contact;
    private String userName;
    private String customerName;
    private String month;
    private String week;
    private LocalDateTime startLDT;
    private ZonedDateTime displayAppointmentStartTime;
    private LocalDateTime endLDT;
    private ZonedDateTime displayAppointmentEndTime;
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public Appointment() {

    }

    public Appointment(int appointmentId, String userName, String customerName, Timestamp start, Timestamp end, String type, int customerId, int userId, String month, String week) {
        this.appointmentId = appointmentId;
        this.userName = userName;
        this.customerName = customerName;
        this.start = start;
        this.end = end;
        this.type = type;
        this.month = month;
        this.week = week;
        this.customerId = customerId;
        this.userId = userId;
        startLDT = start.toLocalDateTime();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = startLDT.atZone(ZoneId.of("UTC"));
        displayAppointmentStartTime = zdt.withZoneSameInstant(zoneId);
        endLDT = end.toLocalDateTime();
        ZonedDateTime zdt2 = endLDT.atZone(ZoneId.of("UTC"));
        displayAppointmentEndTime = zdt2.withZoneSameInstant(zoneId);
    }

    public Appointment(int customerId, int userId, String type, Timestamp start, Timestamp end) {
        this.customerId = customerId;
        this.userId = userId;
        this.title = "blah";
        this.type = type;
        this.start = start;
        this.end = end;
        this.description = "blah";
        this.location = "blah";
        this.url = "blah";
        this.lastUpdateBy = "admin";
        this.contact = "blah";
    }

    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getCustomerId() { return customerId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getUserId() { return userId; }
    public void setTitle() { this.title = title; }
    public String getTitle() { return title; }
    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
    public void setStart(Timestamp start) { this.start = start; }
    public Timestamp getStart() { return start; }
    public void setEnd(Timestamp end) { this.end = end; }
    public Timestamp getEnd() { return end; }
    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }
    public void setLocation(String location) { this.location = location; }
    public String getLocation() { return location; }
    public void setUrl(String url) { this.url = url; }
    public String getUrl() { return url; }
    public void setContact(String contact) { this.contact = contact; }
    public String getContact() { return contact; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerName() { return customerName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserName() { return userName; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public int getAppointmentId() { return appointmentId; }
    public void setMonth(String month) { this.month = month; }
    public String getMonth() { return month; }
    public void setWeek(String week) { this.week = week; }
    public String getWeek() { return week; }
    public void setDisplayAppointmentStartTime(ZonedDateTime zdt) { displayAppointmentStartTime = zdt; }
    public ZonedDateTime getDisplayAppointmentStartTime() { return displayAppointmentStartTime; }
    public void setDisplayAppointmentEndTime(ZonedDateTime zdt) { displayAppointmentEndTime = zdt; }
    public ZonedDateTime getDisplayAppointmentEndTime() { return displayAppointmentEndTime; }

    public static ObservableList<Appointment> populateAppointmentList() {
        try {
            appointmentList.clear();
            PreparedStatement statement = DBConnection.conn.prepareStatement("SELECT appointmentId, appointment.userId, userName, appointment.customerId, customerName, start, end, type, MONTHNAME(start) as 'Month', WEEK(start) as 'Week' FROM appointment, customer, user WHERE appointment.customerId = customer.customerId AND appointment.userId = user.userId");
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                appointmentList.add(new Appointment(rs.getInt("appointmentId"),rs.getString("userName"),rs.getString("customerName"),rs.getTimestamp("start"), rs.getTimestamp("end"), rs.getString("type"), rs.getInt("customerId"), rs.getInt("userId"),rs.getString("Month"),rs.getString("Week")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return appointmentList;
    }


}

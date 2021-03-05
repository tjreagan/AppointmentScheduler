package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Reports {

    private String string;
    private int number;
    private String type;
    private Timestamp timestamp;

    public Reports() {

    }

    public Reports(String string, int number) {
        this.string = string;
        this.number = number;
    }

    public Reports(String string, int number, String type) {
        this.string = string;
        this.number = number;
        this.type = type;
    }

    public Reports(String string, Timestamp timestamp) {
        this.string = string;
        this.timestamp = timestamp;
    }

    public void setString(String string) { this.string = string; }
    public String getString() { return string; }
    public void setNumber(int number) { this.number = number; }
    public int getNumber() { return number; }
    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
    public Timestamp getTimestamp() { return timestamp; }
}
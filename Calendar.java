package model;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Calendar {

    private final IntegerProperty appointmentID;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty type;
    private final ObjectProperty<Timestamp> start;
    private final ObjectProperty<Timestamp> end;
    private final IntegerProperty customerID;
    private final IntegerProperty userID;
    private final StringProperty contact;

    public Calendar(int appointmentID, String title, String description, String location, String type,
                    Timestamp start, Timestamp end, int customerID, int userID, String contact){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contact = new SimpleStringProperty(contact);
    }

    public int getAppointmentID() {
        return appointmentID.get();
    }

    public IntegerProperty appointmentIDProperty() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID.set(appointmentID);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public Timestamp getStart() {
        return start.get();
    }

    public ObjectProperty<Timestamp> startProperty() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start.set(start);
    }

    public Timestamp getEnd() {
        return end.get();
    }

    public ObjectProperty<Timestamp> endProperty() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end.set(end);
    }

    public int getCustomerID() {
        return customerID.get();
    }

    public IntegerProperty customerIDProperty() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID.set(customerID);
    }


    public int getUserID() {
        return userID.get();
    }

    public IntegerProperty userIDProperty() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID.set(userID);
    }


    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }





}


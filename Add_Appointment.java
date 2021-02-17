package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Calendar;
import model.Customer;
import util.dbConnection;
import util.dbQuery;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class Add_Appointment implements Initializable {
    @FXML
    Button home;
    @FXML
    Button saveButton;

    @FXML TextField appointmentIDTF;
    @FXML TextField titleTF;
    @FXML TextField descriptionTF;
    @FXML TextField locationTF;
    @FXML TextField contactTF;
    @FXML TextField typeTF;
    @FXML TextField startDateTF;
    @FXML TextField endDateTF;
    @FXML TextField customerIDTF;
    @FXML TextField userIDTF;

    @FXML
    private TableView<Calendar> tableCalendar;
    @FXML
    private TableColumn<Calendar, Integer> colAppt;
    @FXML
    private TableColumn<Calendar, String> colTitle;
    @FXML
    private TableColumn<Calendar, Integer> colDescription;
    @FXML
    private TableColumn<Calendar, Double> colLocation;
    @FXML
    private TableColumn<Calendar, Integer> colType;
    @FXML
    private TableColumn<Calendar, Integer> colStart;
    @FXML
    private TableColumn<Calendar, Integer> colEnd;
    @FXML
    private TableColumn<Calendar, Integer> colID;

    @FXML
    private ObservableList<Calendar> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void populateTableView() throws SQLException, NullPointerException {
        list = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

            list.add(new Calendar(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getTimestamp(6),
                    rs.getTimestamp(7), rs.getInt(8)));


            tableCalendar.setItems(null);
            tableCalendar.setItems(list);
        }
        colAppt.setCellValueFactory(new PropertyValueFactory<>("appointmentID")); //1
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title")); //2
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description")); //3
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location")); //4
        colType.setCellValueFactory(new PropertyValueFactory<>("type")); //5
        colStart.setCellValueFactory(new PropertyValueFactory<>("start")); //6
        colEnd.setCellValueFactory(new PropertyValueFactory<>("end")); //7
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID")); //8

    }

    private void addAppointment() throws SQLException {

        String insertStatement = ("INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?) " +
                "ON DUPLICATE KEY UPDATE Customer_ID = Customer_ID + 1");
        dbQuery.setPreparedStatement(dbConnection.conn, insertStatement, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement ps = dbQuery.getPreparedStatement();


        //Todo CHECK THE STRING/INT OF THESE
        String appointmentID = appointmentIDTF.getText();
        String title = titleTF.getText();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        Timestamp contact = getCurrentTime();
        String type = null;
        Timestamp startDate = getCurrentTime();
        String endDate = null;
        int customerID = 60;
        int userID = 0;

        //TODO CHECK THE STRING/INTO OF THESE
        ps.setString(1, appointmentID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setTimestamp(5, contact);
        ps.setString(6, type);
        ps.setTimestamp(7, startDate);
        ps.setString(8, endDate);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);

        ps.execute();

        //Outputs if update was successful
        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) affected");
        } else {
            System.out.println("No change!");
        }
    }

//}

    public void saveButton(javafx.event.ActionEvent event) throws IOException, SQLException {
        String appointmentID2 = appointmentIDTF.getText();
        String title2 = titleTF.getText();
        String description2 = descriptionTF.getText();
        String location2 = locationTF.getText();
        String contact2 = contactTF.getText();
        String start2 = startDateTF.getText();
        String end2= endDateTF.getText();
        String customer2 = customerIDTF.getText();
        String user2 = userIDTF.getText();
        String type2 = typeTF.getText();


        if (appointmentID2.equals("") || title2.equals("") || description2.equals("") || location2.equals("") ||
                contact2.equals("") || start2.equals("") || end2.equals("") || customer2.equals("") || user2.equals("")
                || type2.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out in order to save");
            alert.showAndWait();
        } else {

            addAppointment();

            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }
    }

        /** Returns Current Time and Date */
        public Timestamp getCurrentTime(){
            java.util.Date date = new Date();
            return new Timestamp(date.getTime());
        }

                    /** Takes user to Main Menu Screen */
        public void mainMenu (javafx.event.ActionEvent event) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }


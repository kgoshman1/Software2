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
    @FXML TextField typeTF;
    @FXML TextField startDateTF;
    @FXML TextField endDateTF;
    @FXML TextField customerIDTF;
    @FXML TextField userIDTF;
    @FXML ComboBox<String> contactCB;

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
    private TableColumn<Calendar, Integer> colUserID;
    @FXML
    private TableColumn<Calendar, String > colContact;

    @FXML
    public ObservableList<Calendar> list = FXCollections.observableArrayList();
    public ObservableList<String>contacts = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
            addContacts();
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
                    rs.getTimestamp(7), rs.getInt(12),rs.getInt(13), rs.getString(14)));


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
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    public void addAppointment() throws SQLException {

        String insertStatement = ("INSERT INTO appointments(Title, Description, Location, Type, Start, End," +
                " Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        dbQuery.setPreparedStatement(dbConnection.conn, insertStatement, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement ps = dbQuery.getPreparedStatement();


        String title = titleTF.getText();
        String description = descriptionTF.getText();
        String location = locationTF.getText();
        String type = typeTF.getText() ;
        Timestamp start = getCurrentTime();
        Timestamp end = getCurrentTime();
        int customerID = Integer.parseInt(customerIDTF.getText()) ;
        int userID = Integer.parseInt(userIDTF.getText());



        ps.setString(1, title); //2
        ps.setString(2, description); //3
        ps.setString(3, location); //4
        ps.setString(4, type); //5
        ps.setTimestamp(5, start); //6
        ps.setTimestamp(6, end); //7
        ps.setTimestamp(7, getCurrentTime()); //8
        ps.setString(8,"Kyle"); //9
        ps.setTimestamp(9, getCurrentTime()); //10
        ps.setString(10, "Kyle"); //11
        ps.setInt(11, customerID); //12
        ps.setInt(12, userID); //13
        if (contactCB.getValue().equals("Anika Costa")) {
            ps.setInt(13, 1);
        } else if (contactCB.getValue().equals("Daniel Garcia")) {
            ps.setInt(13, 2);
        } else if (contactCB.getValue().equals("Li Lee")) {
            ps.setInt(13, 3);
        }
        ps.execute();

        //Outputs if update was successful
        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) affected");
        } else {
            System.out.println("No change!");
        }
    }

    public void saveButton(javafx.event.ActionEvent event) throws IOException, SQLException {
        String appointment2 = appointmentIDTF.getText();
        String title2 = titleTF.getText();
        String description2 = descriptionTF.getText();
        String location2 = locationTF.getText();
        String type2 = typeTF.getText();
        String start2 = startDateTF.getText();
        String end2 = endDateTF.getText();
        String customerID2 = customerIDTF.getText();
        String userID2 = userIDTF.getText();




        if (appointment2.equals("") || title2.equals("") || description2.equals("") || location2.equals("")
                || type2.equals("") || start2.equals("") || end2.equals("") || customerID2.equals("")
                || userID2.equals("")){
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



    public void addContacts(){

        contacts.add("Anika Costa");
        contacts.add("Daniel Garcia");
        contacts.add("Li Lee");

        contactCB.setItems(contacts);
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


package view_controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ZoomEvent;
import javafx.stage.Stage;
import model.Calendar;

import util.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class Main_Menu implements Initializable {

    @FXML private TableView<Calendar> tableCalendar;
    @FXML private TableColumn<Calendar, Integer> colAppt;
    @FXML private TableColumn<Calendar, String> colTitle;
    @FXML private TableColumn<Calendar, Integer> colDescription;
    @FXML private TableColumn<Calendar, Double> colLocation;
    @FXML private TableColumn<Calendar, Integer> colType;
    @FXML private TableColumn<Calendar, Integer> colStart;
    @FXML private TableColumn<Calendar, Integer> colEnd;
    @FXML private TableColumn<Calendar, Integer> colID;

    @FXML private final DateTimeFormatter dateTimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    //"yyyy-MM-dd'T'HH:mm:ss.S");
    @FXML private ObservableList<Calendar> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void weeklyCalendarFilter(javafx.event.ActionEvent event){

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Calendar> filteredData = new FilteredList<>(list);
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getStart().toString(), dateTimeDTF);
            return rowDate.isAfter(now.minusDays(1))&& rowDate.isBefore(nowPlus7);
        });
        tableCalendar.setItems(filteredData);
    }

    public void viewAllCalendar(javafx.event.ActionEvent event) throws SQLException {
        populateTableView();
    }


    public void monthlyCalendarFilter(javafx.event.ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlusMonth = now.plusMonths(1);
        FilteredList<Calendar> filteredData = new FilteredList<>(list);
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getStart().toString(), dateTimeDTF);
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlusMonth);
        });
        tableCalendar.setItems(filteredData);
    }


    private void populateTableView() throws SQLException, NullPointerException, DateTimeParseException {
        try {
            list = FXCollections.observableArrayList();

            String query = "SELECT * FROM appointments";
            ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

            while (rs.next()) {

//                list.add(new Calendar(rs.getInt(1), rs.getString(2), rs.getString(3),
//                        rs.getString(4), rs.getString(5), rs.getString(6),
//                        rs.getString(7), rs.getInt(10), rs.getInt(12), rs.getString(13))); // LocalDateTime.(rs.getTimestamp(7).toLocalDateTime().atZone(ZoneId.systemDefault()))

                int a = rs.getInt(1);
                String b = rs.getString(2);
                String c = rs.getString(3);
                String d = rs.getString(4);
                String e = rs.getString(5);
                Timestamp f = rs.getTimestamp(6);
                Timestamp g = rs.getTimestamp(7);
                int h = rs.getInt(10);
                int i = rs.getInt(12);
                String j = rs.getString(13);



                //DateTimeFormatter dtflol = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.from(ZoneOffset.UTC));
                DateTimeFormatter formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.from(ZoneOffset.UTC));
                Instant instant = f.toInstant();
                String outStart = formattedDateTime.format(instant);

                Instant instantEnd = g.toInstant();
                String outEnd = formattedDateTime.format(instantEnd);



//                ZoneId zoneId = ZoneId.of(TimeZone.getDefault().getID());
//                ZonedDateTime zonedDateTime = instant.atZone(zoneId);

//
//                Instant instant2 = g.toInstant();
//                ZonedDateTime zonedDateTime2 = instant2.atZone(zoneId);
//
//                ZonedDateTime zonedDateTime = f.toLocalDateTime().atZone(ZoneId.systemDefault());
//                ZonedDateTime zonedDateTime1 = g.toLocalDateTime().atZone(ZoneId.systemDefault());
//
//                LocalDate number1 = zonedDateTime.toLocalDate();
//
//                LocalTime number12 = zonedDateTime.toLocalTime();
//
//
//
//
//                LocalDate numberkk = zonedDateTime2.toLocalDate();
//                LocalTime number12333 = zonedDateTime2.toLocalTime();
//                String numberCombined3 = numberkk + " " + number12333;


                list.add(new Calendar(a,b,c,d,e,outStart,outEnd,i,h,j));

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

        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }


    /************BELOW ARE THE SCREENS VIEWS ******************/
    public void addAppointment(javafx.event.ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Add_Appointment.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void modifyAppointment(javafx.event.ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("Modify_Appointment.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void displayReports(javafx.event.ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("Reports.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void addCustomer(javafx.event.ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("Add_Customer.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void modifyCustomer(javafx.event.ActionEvent event) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("Modify_Customer.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @FXML
    void exitButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to close the application?");
        alert.showAndWait();
        Optional<ButtonType> delete = alert.showAndWait();
        if (delete.isPresent() && delete.get() == ButtonType.OK)
        System.exit(0);
    }

}

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
import javafx.stage.Stage;
import model.Calendar;
import model.Customer;
import util.dbConnection;
import util.dbConnection.*;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import main.Main;
import util.dbQuery;


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

    @FXML private final DateTimeFormatter dateTimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @FXML private ObservableList<Calendar> list = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

            //TODO TAKE CARE OF THIS ONE
    public void FilterAppointmentByWeek(ObservableList appointments){
        LocalDate now = LocalDate.now();
        LocalDate nowPlusWeek = now.plusWeeks(1);

        FilteredList<Calendar> filteredList = new FilteredList<>(appointments);
        filteredList.setPredicate(row -> {

            LocalDate rowDate = LocalDate.parse((CharSequence) row.getStart(), dateTimeDTF);
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlusWeek);
        });
        tableCalendar.setItems(filteredList);
    };

    public void weeklyCalendarFilter(javafx.event.ActionEvent event){
        FilterAppointmentByWeek(list);
    }






    private void populateTableView() throws SQLException, NullPointerException {
        list = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

            list.add(new Calendar(rs.getInt(1),rs.getString(2),rs.getString(3),
                            rs.getString(4),rs.getString(5),rs.getTimestamp(6),
                            rs.getTimestamp(7),rs.getInt(10),rs.getInt(12),rs.getString(13)));



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

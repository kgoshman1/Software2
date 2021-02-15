package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Calendar;
import model.Customer;
import util.dbConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Add_Appointment implements Initializable {
    @FXML Button home;
    @FXML Button saveButton;

    @FXML
    TextField appointmentID;
    @FXML
    TextField title;
    @FXML
    TextField description;
    @FXML
    TextField location;
    @FXML
    TextField contact;
    @FXML
    TextField type;
    @FXML
    TextField startDate;
    @FXML
    TextField endDate;
    @FXML
    TextField customerID;
    @FXML
    TextField userID;

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

                    /** Takes user to Main Menu Screen */
        public void mainMenu (javafx.event.ActionEvent event) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }


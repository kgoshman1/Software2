package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import util.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;

public class Reports implements Initializable {

    @FXML ObservableList<String> Months = FXCollections.observableArrayList();
    @FXML Button home;
    @FXML TextArea textArea1;
    @FXML TextArea textArea2;
    @FXML TextArea textArea3;


    /** Creates and dispalys the month Report. */
    public void monthReport(ActionEvent event) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT monthname(Start) as 'Months', year(Start) as 'Year', count(*) as 'Count' \n" +
                "FROM appointments\n" +
                "GROUP BY Months, Year \n" +
                "ORDER BY count(*) DESC;";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);
        while (rs.next()) {
            stringBuilder.append(rs.getString("Months"))
                    .append("           ")
                    .append(rs.getString("Year"))
                    .append("           ")
                    .append(rs.getString("Count"))
                    .append("           \n");
        }
        textArea1.setText(String.valueOf(stringBuilder));
    }

    public void typeReport(ActionEvent event) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT Type as 'Type', count(*) as 'Count' \n" +
                "FROM appointments\n" +
                "GROUP BY Type \n" +
                "ORDER BY count(*) DESC;";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);
        while (rs.next()) {
            stringBuilder.append(rs.getString("Type"))
                    .append("           ")
                    .append(rs.getString("Count"))
                    .append("           \n");

        }
        textArea1.setText(String.valueOf(stringBuilder));
    }

    /** Creates and displays scheduleReport. */
    public void scheduleReport(ActionEvent event) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        String query = "SELECT Contact_Name as 'Contact_Name', Appointment_ID as 'Appointment_ID', Title as 'Title', Type as 'Type', Description as 'Description', Start as 'Start', " +
                "End as 'End', Customer_ID as 'Customer_ID' FROM contacts INNER JOIN appointments GROUP BY Start, Appointment_ID, Contact_Name, Title, Type, Description, Start, End " +
        " ORDER BY Contact_Name, Start;";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);
        while (rs.next()) {
            stringBuilder.append(rs.getString("Contact_Name"))
                    .append("                ")
                    .append(rs.getString("Appointment_ID"))
                    .append("                ")
                    .append(rs.getString("Title"))
                    .append("                ")
                    .append(rs.getString("Type"))
                    .append("                           ")
                    .append(rs.getString("Description"))
                    .append("                           ")
                    .append(rs.getString("Start"))
                    .append("                           ")
                    .append(rs.getString("End"))
                    .append("                           ")
                    .append(rs.getString("Customer_ID"))
                    .append("           \n");

        }
        textArea2.setText(String.valueOf(stringBuilder));
    }

    /** Creates and displays additional report. */
    public void otherReport(ActionEvent event) throws SQLException {
        StringBuilder stringBuilder1 = new StringBuilder();
        String query1 = "SELECT COUNT(count) FROM (SELECT COUNT(Division_ID) as count " +
                "FROM customers " +
                "GROUP BY Division_ID " +
                "HAVING Division_ID < 55) AS C;";
        ResultSet rs1 = dbConnection.conn.createStatement().executeQuery(query1);
        while (rs1.next()) {
            stringBuilder1.append(rs1.getString("COUNT(count)"));


            StringBuilder stringBuilder2 = new StringBuilder();
            String query2 = "SELECT COUNT(count) FROM (SELECT COUNT(Division_ID) as count " +
                    "FROM customers " +
                    "GROUP BY Division_ID " +
                    "HAVING Division_ID BETWEEN 53 AND 79) AS C;";
            ResultSet rs2 = dbConnection.conn.createStatement().executeQuery(query2);
            while (rs2.next()) {
                stringBuilder2.append(rs2.getString("COUNT(count)"));


            StringBuilder stringBuilder3 = new StringBuilder();
            String query3 = "SELECT COUNT(count) FROM (SELECT COUNT(Division_ID) as count " +
                        "FROM customers " +
                        "GROUP BY Division_ID " +
                        "HAVING Division_ID BETWEEN 101 AND 104) AS C;";
            ResultSet rs3 = dbConnection.conn.createStatement().executeQuery(query3);
            while (rs3.next()) {
                stringBuilder3.append(rs3.getString("COUNT(count)"));
            }

                String stringBuilder1Text = String.valueOf(stringBuilder1);
                String stringBuilder2Text = String.valueOf(stringBuilder2);
                String stringBuilder3Text = String.valueOf(stringBuilder3);

                textArea3.setText("Customers from the US " + stringBuilder1Text + ", \n\n" +
                        "Customers from Canada " + stringBuilder2Text + ", \n\n" +
                        "Customers from the UK " + stringBuilder3Text);
            }
        }
    }

    /** Takes user to home page when home button is clicked. */
    public void mainMenu(javafx.event.ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

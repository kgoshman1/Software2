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

    @FXML
    ObservableList<String> Months = FXCollections.observableArrayList();
    @FXML
    ComboBox<String> monthsCB;
    @FXML
    Button home;
    @FXML
    TextArea textArea1;

    public void mainMenu(javafx.event.ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

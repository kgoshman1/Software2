package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/Login.fxml"));
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {

        Connection conn = dbConnection.startConnection();

        launch(args);
        dbConnection.closeConnection();

    }
}

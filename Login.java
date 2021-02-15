package view_controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;


public class Login implements Initializable {
    @FXML
    PasswordField passwordTF;
    @FXML
    TextField userNameTF;
    @FXML
    Button submitButton;
    @FXML
    Label locationLabel;
    @FXML
    Label timeLabel;
    @FXML
    Label loginLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resourceBundle();
        updateUserTime();
    }


    @FXML
    private void updateUserTime() {
        TimeZone timezone = TimeZone.getDefault();
        String timezones = timezone.getID();
        timeLabel.setText(timezones);
    }


    public void authenticate (javafx.event.ActionEvent event) throws IOException, NullPointerException {
        if (userNameTF.getText().equals("test") && (passwordTF.getText().equals("test"))) {

            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
            else {
                alertRB();
            }
        }


    public void resourceBundle () {
        ResourceBundle rb = ResourceBundle.getBundle("resources/login", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("en") || (Locale.getDefault().getLanguage().equals("fr"))) {
            passwordTF.setPromptText(rb.getString("passwordTF"));
            userNameTF.setPromptText(rb.getString("userNameTF"));
            submitButton.setText(rb.getString("submitButton"));
            locationLabel.setText(rb.getString("locationLabel"));

        }
    }

    public void alertRB(){
        ResourceBundle rb = ResourceBundle.getBundle("resources/login", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("en") || (Locale.getDefault().getLanguage().equals("fr"))) {
            loginLabel.setText(rb.getString("loginLabel"));
        }
    }

}
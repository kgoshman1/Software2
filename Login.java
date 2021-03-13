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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Calendar;
import util.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Login implements Initializable {
    @FXML PasswordField passwordTF;
    @FXML TextField userNameTF;
    @FXML Button submitButton;
    @FXML Label locationLabel;
    @FXML Label timeLabel;
    @FXML Label loginLabel;
    @FXML private ObservableList<Calendar> list = FXCollections.observableArrayList();

    public Login() throws SQLException {
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        resourceBundle();
        updateUserTime();
    }

    @FXML private void updateUserTime() {
        TimeZone timezone = TimeZone.getDefault();
        String timezones = timezone.getID();
        timeLabel.setText(timezones);
    }

    /**  Permits or denies entry into main menu based on username combination. */
    public void authenticate(javafx.event.ActionEvent event) throws IOException, NullPointerException, SQLException {

        String userName = userNameTF.getText();
        String Password = passwordTF.getText();
        String query = "SELECT User_Name, Password FROM users";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

                Logger logger = Logger.getLogger("Login_Activity");

                FileHandler fileHandler = new FileHandler("/Users/kylegoshman/IdeaProjects/software2/login_activity.txt", true);
                logger.addHandler(fileHandler);
                SimpleFormatter formatter = new SimpleFormatter();
                fileHandler.setFormatter(formatter);

                Boolean successful = rs.getString("User_Name").equals(userName) && rs.getString("Password").equals(Password);

                if (successful) {

                    success();
                    Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
                    Scene scene = new Scene(parent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                    break;

                } else {
                    failure();
                    alertRB();
                    userNameTF.clear();
                    passwordTF.clear();
                }
            }
        }

    /** Changes language of text based on computer settings. */
    public void resourceBundle() {
        ResourceBundle rb = ResourceBundle.getBundle("resources/login", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("en") || (Locale.getDefault().getLanguage().equals("fr"))) {
            passwordTF.setPromptText(rb.getString("passwordTF"));
            userNameTF.setPromptText(rb.getString("userNameTF"));
            submitButton.setText(rb.getString("submitButton"));
            locationLabel.setText(rb.getString("locationLabel"));

        }
    }

    /** Sets login label to user language settings. */
    public void alertRB() {
        ResourceBundle rb = ResourceBundle.getBundle("resources/login", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("en") || (Locale.getDefault().getLanguage().equals("fr"))) {
            loginLabel.setText(rb.getString("loginLabel"));
        }
    }

    /** Logs custom message if user successfully logged in. */
    public  void success(){
        String userName = userNameTF.getText();
        Logger logger = Logger.getLogger("Login_Activity");
        logger.info("Successful Login" + " By" + " " + userName);
    }

    /** Logs custom message if user failed to log in. */
    public  void failure(){
        String userName = userNameTF.getText();
        Logger logger = Logger.getLogger("Login_Activity");
        logger.info("Failed Attempt" + " By" + " " + userName);
    }
}
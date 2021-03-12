package view_controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Calendar;
import util.dbConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;


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


    @FXML  ObservableList<Calendar> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTableView();
            loginAlert();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void weeklyCalendarFilter(javafx.event.ActionEvent event){

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus7 = now.plusDays(7);
        FilteredList<Calendar> filteredData = new FilteredList<>(list);
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getStart(), dateTimeDTF);
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
            LocalDateTime rowDate = LocalDateTime.parse(row.getStart(), dateTimeDTF);
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlusMonth);
        });
        tableCalendar.setItems(filteredData);
    }

    public void loginAlert(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(15);

        FilteredList<Calendar> filteredData = new FilteredList<>(list);
        filteredData.setPredicate(date -> {
            LocalDateTime rowDate = LocalDateTime.parse(date.getStart(), dateTimeDTF); //I CHANGED THIS
            return rowDate.isAfter(currentTime.minusMinutes(1)) && rowDate.isBefore(startTime);
        });

        if (filteredData.isEmpty()) {
            System.out.println("No Appointments");
        } else {
//            String title = filteredData.get(0).getTitle();
//            String descrip = filteredData.get(0).getDescription();
//            String type = filteredData.get(0).getType();
//            String start = filteredData.get(0).getStart();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"You have an appointment within the next 15 Minutes");
//            alert.setContentText(title + "" + descrip + "" + type + "" + start );
            alert.showAndWait();
        }
    }



    private void populateTableView() throws SQLException, NullPointerException, DateTimeParseException {

        list = FXCollections.observableArrayList();

        String query = "SELECT * FROM appointments";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

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

            DateTimeFormatter formattedDateTimes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s").withZone(ZoneId.systemDefault());

            try {


                String string = f.toString();
                LocalDateTime localDateTimeStart = LocalDateTime.parse(string, formattedDateTime);
                ZonedDateTime zdt = localDateTimeStart.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
                String outStart = zdt.format(formattedDateTimes);

                String strings = g.toString();
                LocalDateTime localDateTimeStarts = LocalDateTime.parse(strings, formattedDateTime);
                ZonedDateTime zdts = localDateTimeStarts.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());
                String outEnd = zdts.format(formattedDateTimes);

                ZonedDateTime djlj = localDateTimeStarts.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault());


                list.add(new Calendar(a, b, c, d, e, outStart, outEnd, i, h, j));

                tableCalendar.setItems(null);
                tableCalendar.setItems(list);


                colAppt.setCellValueFactory(new PropertyValueFactory<>("appointmentID")); //1
                colTitle.setCellValueFactory(new PropertyValueFactory<>("title")); //2
                colDescription.setCellValueFactory(new PropertyValueFactory<>("description")); //3
                colLocation.setCellValueFactory(new PropertyValueFactory<>("location")); //4
                colType.setCellValueFactory(new PropertyValueFactory<>("type")); //5
                colStart.setCellValueFactory(new PropertyValueFactory<>("start")); //6
                colEnd.setCellValueFactory(new PropertyValueFactory<>("end")); //7
                colID.setCellValueFactory(new PropertyValueFactory<>("customerID")); //8

            } catch (DateTimeParseException k) {
                k.printStackTrace();
            }
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

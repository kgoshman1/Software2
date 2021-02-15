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
import model.Customer;
import util.dbConnection;
import util.dbQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class Modify_Customer implements Initializable {
    @FXML private TableView<Customer> tableCustomer;
    @FXML private TableColumn<Customer,Integer> colID;
    @FXML private TableColumn<Customer,String> colName;
    @FXML private TableColumn<Customer,String> colAddress;
    @FXML private TableColumn<Customer,String> colZip;
    @FXML private TableColumn<Customer,String> colPhone;
    @FXML private TableColumn<Customer, Timestamp> colCreatedDate;
    @FXML private TableColumn<Customer,String> colCreatedBy;
    @FXML private TableColumn<Customer,Timestamp> colLastUpdate;
    @FXML private TableColumn<Customer,String> colUpdatedBy;
    @FXML private TableColumn<Customer,Integer> colDivisionID;

    @FXML private TextField nameTF;
    @FXML private TextField addressTF;
    @FXML private TextField zipTF;
    @FXML private TextField phoneTF;

    @FXML private ComboBox<String> countryCB;
    @FXML private ComboBox<String> stateCB;

    @FXML Button home;

    @FXML private ObservableList<Customer> list;
    @FXML ObservableList<String> Countries = FXCollections.observableArrayList();
    @FXML ObservableList<String> States = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCountryCB();
            getStateCB();
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView() throws SQLException, NullPointerException {
        list = FXCollections.observableArrayList();

        String query = "SELECT * FROM customers";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

            list.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)
                    ,rs.getString(5),rs.getTimestamp(6),rs.getString(7),rs.getTimestamp(8),
                    rs.getString(9),rs.getInt(10)));

            tableCustomer.setItems(null);
            tableCustomer.setItems(list);
        }
        colID.setCellValueFactory(new PropertyValueFactory<>("customerID")); //1
        colName.setCellValueFactory(new PropertyValueFactory<>("customersName")); //2
        colAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress")); //3
        colZip.setCellValueFactory(new PropertyValueFactory<>("customerZip")); //4
        colPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone")); //5
        colCreatedDate.setCellValueFactory(new PropertyValueFactory<>("customerCreatedDate")); //6
        colCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy")); //7
        colLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate")); //8
        colUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy")); //9
        colDivisionID.setCellValueFactory(new PropertyValueFactory<>("divisionID")); //10
    }

    public void modifyCustomer() throws SQLException {

        String insertStatement = ("INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE Customer_ID = Customer_ID + 1");
        dbQuery.setPreparedStatement(dbConnection.conn, insertStatement, Statement.RETURN_GENERATED_KEYS);
        PreparedStatement ps = dbQuery.getPreparedStatement();

        String customerName = nameTF.getText();
        String customerAddress = addressTF.getText();
        String customerZip = zipTF.getText();
        String customerPhone = phoneTF.getText();
        Timestamp createdDate = getCurrentTime() ;
        String createdBy = "Script";
        Timestamp lastUpdate = getCurrentTime();
        String updatedBy =  "Script";
        int divisionID = 60;


        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerZip);
        ps.setString(4, customerPhone);
        ps.setTimestamp(5,createdDate);
        ps.setString(6, createdBy);
        ps.setTimestamp(7, lastUpdate);
        ps.setString(8, updatedBy);
        ps.setInt(9, divisionID);

        ps.execute();

        //Outputs if update was successful
        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " row(s) affected");
        } else {
            System.out.println("No change!");
        }
    }


    public void saveButton(javafx.event.ActionEvent event) throws IOException, SQLException {
        String nameTF2 = nameTF.getText();
        String addressTF2 = addressTF.getText();
        String zipTF2 = zipTF.getText();
        String phoneTF2 = phoneTF.getText();
        boolean countryCB2 = countryCB.getSelectionModel().isEmpty();
        boolean stateCB2 = stateCB.getSelectionModel().isEmpty();


        if (nameTF2.equals("") || addressTF2.equals("") || zipTF2.equals("") || phoneTF2.equals("") || countryCB2 || stateCB2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled out in order to save");
            alert.showAndWait();
        } else {

            modifyCustomer();

            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    /** Populates States ComboBox with state data */
    public void getStateCB() throws SQLException {
        Statement statement = dbConnection.conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM first_level_divisions");
        while (rs.next()) {

            States.add(rs.getString("Division"));
            int getPrimary = rs.getInt("Division_ID");
            stateCB.setItems(States);
        }
    }

    /** Populates Country ComboBox with country data */
    public void getCountryCB() throws SQLException {
        Countries.add("United States");
        Countries.add("Canada");
        Countries.add("United Kingdom");
        countryCB.setItems(Countries);
    }

    /** Returns Current Time and Date */
    public Timestamp getCurrentTime(){
        java.util.Date date = new Date();
        return new Timestamp(date.getTime());
    }

    /** Returns user to the  Main Menu */
    public void mainMenu (javafx.event.ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }




    public void save(){}


}

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

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.sql.PreparedStatement;


public class Add_Customer implements Initializable {

/** Button and Text */
@FXML Button home;
@FXML TextField idTF;
@FXML TextField nameTF;
@FXML TextField addressTF;
@FXML TextField zipTF;
@FXML TextField phoneTF;

@FXML ComboBox<String>stateCB;
@FXML ComboBox<String>countryCB;

@FXML ObservableList<Customer> customers;
@FXML ObservableList<String> Countries = FXCollections.observableArrayList();
@FXML ObservableList<String> States = FXCollections.observableArrayList();
@FXML ObservableList<String> Provinces = FXCollections.observableArrayList();
@FXML ObservableList<String> Territories = FXCollections.observableArrayList();

@FXML private ObservableList<Customer> customerList;

@FXML private TableView<Customer> tableCustomer;
@FXML private TableColumn<Customer,Integer> colID;
@FXML private TableColumn<Customer,String> colName;
@FXML private TableColumn<Customer,String> colAddress;
@FXML private TableColumn<Customer,String> colZip;
@FXML private TableColumn<Customer,String> colPhone;
@FXML private TableColumn<Customer,Timestamp> colCreatedDate;
@FXML private TableColumn<Customer,String> colCreatedBy;
@FXML private TableColumn<Customer,Timestamp> colLastUpdate;
@FXML private TableColumn<Customer,String> colUpdatedBy;
@FXML private TableColumn<Customer,Integer> colDivisionID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getCountryCB();
            populateTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void populateTableView() throws SQLException, NullPointerException {
        customerList = FXCollections.observableArrayList();

        String query = "SELECT * FROM customers";
        ResultSet rs = dbConnection.conn.createStatement().executeQuery(query);

        while (rs.next()) {

            customerList.add(new Customer(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)
                    ,rs.getString(5),rs.getTimestamp(6),rs.getString(7),rs.getTimestamp(8),
                    rs.getString(9),rs.getInt(10)));

              tableCustomer.setItems(null);
              tableCustomer.setItems(customerList);
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


    public void addCustomer() throws SQLException {

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
        //ps.setInt(9, divisionID);
        if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Alabama")) {
            ps.setInt(9, 1);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Arizona")) {
            ps.setInt(9, 2);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Arkansas")) {
            ps.setInt(9, 3);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("California")) {
            ps.setInt(9, 4);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Colorado")) {
            ps.setInt(9, 5);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Connecticut")) {
            ps.setInt(9, 6);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Delaware")) {
            ps.setInt(9, 7);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("District of Columbia")) {
            ps.setInt(9, 8);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Florida")) {
            ps.setInt(9, 9);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Georgia")) {
            ps.setInt(9, 10);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Idaho")) {
            ps.setInt(9, 11);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Illinois")) {
            ps.setInt(9, 12);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Indiana")) {
            ps.setInt(9, 13);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Iowa")) {
            ps.setInt(9, 14);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Kansas")) {
            ps.setInt(9, 15);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Kentucky")) {
            ps.setInt(9, 16);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Louisiana")) {
            ps.setInt(9, 17);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Maine")) {
            ps.setInt(9, 18);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Maryland")) {
            ps.setInt(9, 19);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Massachusets")) {
            ps.setInt(9, 20);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Michigan")) {
            ps.setInt(9, 21);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Minnesota")) {
            ps.setInt(9, 22);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Mississippi")) {
            ps.setInt(9, 23);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Missouri")) {
            ps.setInt(9, 24);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Montana")) {
            ps.setInt(9, 25);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Nebraska")) {
            ps.setInt(9, 26);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Nevada")) {
            ps.setInt(9, 27);
        } else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("New Hampshire")) {
            ps.setInt(9, 28);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("New Jersey")) {
            ps.setInt(9, 29);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("New Mexico")) {
            ps.setInt(9, 30);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("New York")) {
            ps.setInt(9, 31);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("North Carolina")) {
            ps.setInt(9, 32);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("North Dakota")) {
            ps.setInt(9, 33);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Ohio")) {
            ps.setInt(9, 34);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Oklahoma")) {
            ps.setInt(9, 35);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Oregon")) {
            ps.setInt(9, 36);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Pennsylvania")) {
            ps.setInt(9, 37);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Rhode Island")) {
            ps.setInt(9, 38);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("South Carolina")) {
            ps.setInt(9, 39);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("South Dakota")) {
            ps.setInt(9, 40);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Tennessee")) {
            ps.setInt(9, 41);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Texas")) {
            ps.setInt(9, 42);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Utah")) {
            ps.setInt(9, 43);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Vermont")) {
            ps.setInt(9, 44);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Virginia")) {
            ps.setInt(9, 45);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Washington")) {
            ps.setInt(9, 46);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("West Virginia")) {
            ps.setInt(9, 47);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Wisconsin")) {
            ps.setInt(9, 48);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Wyoming")) {
            ps.setInt(9, 49);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Hawaii")) {
            ps.setInt(9, 52);
        }else if (countryCB.getValue().equals("United States") && stateCB.getValue().equals("Alaska")) {
            ps.setInt(9, 54);

                            /* END OF UNITED STATES - BEGINNING OF CANADA */

        }else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Northwest Territories")) {
            ps.setInt(9, 60);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Alberta")) {
            ps.setInt(9, 61);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("British Columbia")) {
            ps.setInt(9, 62);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Manitoba")) {
            ps.setInt(9, 63);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("New Brunswick")) {
            ps.setInt(9, 64);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Nova Scotia")) {
            ps.setInt(9, 65);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Prince Edward Island")) {
            ps.setInt(9, 66);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Ontario")) {
            ps.setInt(9, 67);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Québec")) {
            ps.setInt(9, 68);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Saskatchewan")) {
            ps.setInt(9, 69);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Nunavut")) {
            ps.setInt(9, 70);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Yukon")) {
            ps.setInt(9, 71);
        } else if (countryCB.getValue().equals("Canada") && stateCB.getValue().equals("Newfoundland and Labrador")) {
            ps.setInt(9, 72);

                    /* END OF CANADA - BEGINNING OF UNITED KINGDOM */
        } else if (countryCB.getValue().equals("United Kingdom") && stateCB.getValue().equals("England")) {
            ps.setInt(9, 101);
        } else if (countryCB.getValue().equals("United Kingdom") && stateCB.getValue().equals("Wales")) {
            ps.setInt(9, 102);
        } else if (countryCB.getValue().equals("United Kingdom") && stateCB.getValue().equals("Scotland")) {
            ps.setInt(9, 103);
        } else if (countryCB.getValue().equals("United Kingdom") && stateCB.getValue().equals("Northern Ireland")) {
            ps.setInt(9, 104);
        }
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

            addCustomer();

            Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }


    public void getStateCBUS() throws NullPointerException, SQLException {
        try {
            Statement statement = dbConnection.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM first_level_divisions LIMIT 51");
            while (rs.next()) {
                States.add(rs.getString("Division"));
                stateCB.setItems(States);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getStateCBCA() throws NullPointerException, SQLException {
        try {
            Statement statement = dbConnection.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM first_level_divisions LIMIT 51, 13");
            while (rs.next()) {
                Provinces.add(rs.getString("Division"));
                stateCB.setItems(Provinces);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void getStateCBUK() throws NullPointerException, SQLException {
        try {
            Statement statement = dbConnection.conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM first_level_divisions LIMIT 64, 4");
            while (rs.next()) {
                Territories.add(rs.getString("Division"));
                stateCB.setItems(Territories);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void checkCountry(javafx.event.ActionEvent event) throws SQLException {

        if (countryCB.getSelectionModel().getSelectedItem().equals("United States")){
            getStateCBUS();
            System.out.println("Returning US States");
        } else if (countryCB.getSelectionModel().getSelectedItem().equals("Canada")){
            getStateCBCA();
            System.out.println("Returning Canadian Provinces");
        } else if (countryCB.getSelectionModel().getSelectedItem().equals("United Kingdom")){
            getStateCBUK();
            System.out.println("Returning UK Territories");
        }
    }

    public void getCountryCB() throws SQLException {
        Countries.add("United States");
        Countries.add("Canada");
        Countries.add("United Kingdom");
        countryCB.setItems(Countries);
    }

    /** Returns Current Time and Date */
    public Timestamp getCurrentTime(){
        Date date = new Date();
        return new Timestamp(date.getTime());
    }


    /** Main Menu.
     * Upon clicking the main menu button, user is returned to the "main-menu" screen;
     * @param event
     * @throws IOException
     */
    public void mainMenu(javafx.event.ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Main_Menu.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }






}



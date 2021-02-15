package main;

import com.mysql.jdbc.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.dbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import util.dbQuery;

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

//        String insertStatement = "INSERT INTO countries(country, Create_Date, Created_By, Last_Updated_By) VALUES(?, ?, ?, ?) ";
//
//        PreparedStatement ps = (PreparedStatement) dbQuery.getPreparedStatement();
//
//        String Country_Name;
//        String Created_Date = "2020-03-20 00:00:01";
//        String CreatedBy = "admin";
//        String LastUpdateBy = "admin";
//
//        //Keyboard Input
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter a country");
//        Country_Name = scanner.nextLine();
//
//        //Key-Value Mapping **1 NOT 0 Based**
//        ps.setString(1, Country_Name);
//        ps.setString(2, Created_Date);
//        ps.setString(3, CreatedBy);
//        ps.setString(4, LastUpdateBy);
//
//        //Execute Prepared Statement
//        ps.execute();
//
//        //Confirm Rows Affected
//        if (ps.getUpdateCount() > 0) {
//            System.out.println(ps.getUpdateCount() + " row(s) affected");
//        } else {
//            System.out.println("No change!");
//        }

        launch(args);
        dbConnection.closeConnection();

    }
}
      //****INFORMATIONAL EXAMPLES ONLY*****//

/*  //**Raw SQL Insert Statement**

     //Confirm Rows Affected
        if (statement.getUpdateCount() > 0) {
            System.out.println(statement.getUpdateCount() + " row(s) affected");
        } else {
            System.out.println("No change!");
        }

    String insertStatement = "insert into countries(Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (4, 'us', '2021-02-11 14:18:29', 'script', '2021-02-11 14:18:28', 'script');";
    int countryID = 4;
    String countryName = "Canada";
    String createDate = "2021-02-11 14:18:29";
    String createdBy = "script";
    String lastUpdate = "2021-02-11 14:18:29";
    String lastUpdateBy = "script";

    //**SQL Insert Statement Using Variables**
    String insertStatement  = "INSERT INTO countries(Country_ID, Country, Create_Date, Created_By, Last_Update, Last_Updated_By)" +
            "Values(" +
            "'" + countryID + "'," +
            "'" + countryName + "'," +
            "'" + createDate + "'," +
            "'" + createdBy + "'," +
            "'" + lastUpdate + "'," +
            "'" + lastUpdateBy + "'" +
            ")";


    //**SQL Update Statement**
    String updateStatement = "UPDATE countries SET Country = 'Japan' WHERE country = 'Canada'";

    //**Execute SQL Statement (returns boolean - in this case false)**
    statement.execute(insertStatement);

    //**SQL Update Statement**
    String updateStatement = "UPDATE countries SET Country = 'Japan' WHERE country = 'Canada'";

    //Execute SQL Statement (returns boolean - in this case false)
    statement.execute(insertStatement);

     //Forward Scroll Result Set
        while (rs.next())
        {
            int countryID = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            LocalDate date = rs.getDate("Create_Date").toLocalDate(); // "TOLOCALDATE Converts date to local date ** DATE IS DEPRECATED
            LocalTime time = rs.getTime("Create_Date").toLocalTime(); //Same as above ^
            String createdBy = rs.getString("Created_By");
            LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");


            //Display Record
            System.out.println(countryID +" | " + countryName + " | " + date +" | " + time +" | " + createdBy
                    + " | " + lastUpdate +" | " + lastUpdatedBy);
        }

          //Connection conn = dbConnection.startConnection(); //Connect to Database
        Connection conn  =  dbConnection.startConnection();
        dbQuery.setStatement(conn); //Creates Statement Object
        Statement statement = dbQuery.getStatement(); //Retrieve Statement Object

        String selectStatement = "SELECT *  FROM countries"; //Select Statement
        statement.execute(selectStatement);
        ResultSet rs = statement.getResultSet(); //Get result set

 */

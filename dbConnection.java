package util;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    //JDBC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ08JIG";

    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    //Driver Interface Reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    public static Connection conn = null;

    private static final String username = "U08JIG";
    private static final String password = "53689304726";

    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection Successful!");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection Closed!");
        }
        catch (SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
        }

    }
}

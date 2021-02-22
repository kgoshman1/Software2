package util;


import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class  dbQuery {

    private static PreparedStatement statement; //statement reference


    //Create Statement Object
    public static void setPreparedStatement(Connection conn, String sqlStatement, int returnGeneratedKeys) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }


    //Return Statement Object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    public static void setPreparedStatement(com.mysql.jdbc.Connection conn, String insertStatement) throws SQLException {
        statement = conn.prepareStatement(insertStatement);
    }



}


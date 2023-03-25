/*
 Establishes Connection with the Database
 */
package in.impetusits.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Hrishi
 */
public class DataBase {

    public final static String DB_DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    public final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    public final static String DB_USERNAME = "hr";
    public final static String DB_PASSWORD = "hr";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection con = null;

        // load the Driver Class
        Class.forName(DB_DRIVER_CLASS);

        // create the connection now
        con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        System.out.println("DB Connection created successfully");
        return con;
    }

    public static void insertData(Connection con, String name,float salary)
            throws SQLException {
        String query = "insert into temp_emp values(?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setFloat(2, salary);
            
            stmt.executeUpdate();
            
            System.out.println("Employee Data inserted successfully");
        }
    }
}

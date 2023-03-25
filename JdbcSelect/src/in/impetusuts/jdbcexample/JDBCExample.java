package in.impetusuts.jdbcexample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author impetus
 */
public class JDBCExample {

    private final static String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    //"jdbc:oracle:thin:@//localhost:1521/XE"
//    "jdbc:oracle:oci:@//localhost:1521/XE"
    private final static String URL = "jdbc:derby://localhost:1527/sample";
    private final static String USERNAME = "app";
    private final static String PASSWORD = "app";

    public static void main(String args[]) {

        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;

        try {
// Load the Driver
            // String DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
            Class.forName(DRIVER);
            System.out.println("\nLOADED DRIVER ---> " + DRIVER);

// Create the "url"
// assume database server is running on the localhost
            // String url = "jdbc:odbc:DeptDB";
// Connect to the database represented by url
// with username public and password public
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("CONNECTED TO ---> " + URL);

// Use the Connection to create a Statement object
            stmt = con.createStatement();
            String qry = "SELECT * FROM CUSTOMER";
            System.out.println("EXECUTED QUERY ---> " + qry);

            result = stmt.executeQuery(qry);
// Print the results, row by row
            System.out.println("\nPROCESSING RESULTS:\n");
            int count = 0;
            while (result.next()) {
                count++;
                System.out.println(" Name : "
                        + result.getString("name").trim());
                System.out.println(" Id : "
                        + result.getInt("CUSTOMER_ID"));

                System.out.println(" City: "
                        + result.getString("city").trim());
                
                System.out.println("");

            } //while
            System.out.println("Number of Records listed:" + count);
        } catch (SQLException e) {
            System.out.println(e.toString() + "  c1");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString() + "  C2");
        } finally {

            if (result != null) {
                try {
                    result.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBCExample.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
// Execute query using Statement, receive the ResultSet
//String qry ="SELECT * FROM Customer ORDER BY cust_name";
//ResultSet result = stmt.executeQuery(qry);

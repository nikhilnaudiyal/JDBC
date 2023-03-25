package jdbcprepared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Hrishi
 */
public class JdbcPrepared {

    public static void main(String args[]) {
        Connection con = null;
        PreparedStatement stmt = null;
        try {

            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            String url = "jdbc:oracle:thin:@//localhost:1521/XE";
            con = DriverManager.getConnection(url, "HR", "hr");

            String qry = "UPDATE employees SET first_name = ? "
                    + " WHERE employee_id = ?";

            stmt = con.prepareStatement(qry);

            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter employee Id :");
            int empID = scan.nextInt();
            System.out.print("Please enter the name:");
            String name = scan.next();

            stmt.setString(1, name);
            stmt.setInt(2, empID);

            int count = stmt.executeUpdate();
            System.out.println("Number of Rows affected:"+count);

        }
        catch (SQLException e) {
            System.out.println(e.toString() + "c1");
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.toString() + " C2");
        }
        finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
            }
        }

    }
}

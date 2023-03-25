/*
 Using Datasource Object
 */
package in.impetusits.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
//import org.apache.commons.dbcp.BasicDataSource;
/**
 *
 * @author Hrishi
 */
public class DataSourceDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataSource ds = null;

        ds = MyDataSourceFactory.getDataSource();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select employee_id, first_name, last_name from Employees");
            while (rs.next()) {
                System.out.println("Employee ID=" + rs.getInt("employee_id") + ", "
                        + "First Name=" + rs.getString("first_name")+ ", "
                        + "Last Name=" + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}



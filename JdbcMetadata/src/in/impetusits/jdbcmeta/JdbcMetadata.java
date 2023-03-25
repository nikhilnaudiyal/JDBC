package in.impetusits.jdbcmeta;

/**
 *
 * @author Hrishi
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.pool.OracleDataSource;

class JdbcMetadata {

    public static void main(String args[]) throws SQLException {
        OracleDataSource ods = new OracleDataSource();
        ods.setURL("jdbc:oracle:thin:@localhost");
        ods.setServiceName("XE");
        ods.setPortNumber(1521);
        ods.setUser("HR");
        ods.setPassword("hr");
        
        Connection conn = ods.getConnection();
// Create Oracle DatabaseMetaData object
        DatabaseMetaData meta = conn.getMetaData();
//get Data base information
        System.out.println("DataBase Product Name : "
                + meta.getDatabaseProductName());
        System.out.println("DataBase Major Version : "
                + meta.getDatabaseMajorVersion());
        System.out.println("DataBase Minor Version : "
                + meta.getDatabaseMinorVersion());

        // gets driver info:
        System.out.println("Driver : " + 
                meta.getDriverName());
        System.out.println("JDBC driver version is "
                + meta.getDriverVersion());
//        user details
        System.out.println("User name : "
                +meta.getUserName());
//        Jdbc details
        System.out.println("Jdbc Version :" 
                + meta.getJDBCMajorVersion());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
        ResultSetMetaData rsm = rs.getMetaData();
       
        System.out.println("Number of Columns :"+rsm.getColumnCount());
//        System.out.println("Table Name :"+rsm.getTableName(3));
        System.out.println("Name of First column :"+rsm.getColumnName(1));
        System.out.println("Name of First column Type:"+rsm.getColumnTypeName(1));
        
        rs.close();
        stmt.close();
        conn.close();
    }
}

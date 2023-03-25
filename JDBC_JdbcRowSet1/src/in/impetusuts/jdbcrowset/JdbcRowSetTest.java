/*
JdbcRowSet is a connected RowSet that wraps 
around a scrollable and updatable ResultSet 
(TYPE_SCROLL_INSENSITIVE, CONCUR_UPDATABLE). 
An implementation is provided in 
com.sun.rowset.JdbcRowSetImpl.
In JDK 7 (with RowSet 1.1), you can use a 
RowSetFactory to create a JdbcRowSet object.
*/
package in.impetusuts.jdbcrowset;

import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Hrishi
 */
public class JdbcRowSetTest {

//    private final static String DB_DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    private final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final static String DB_USERNAME = "hr";
    private final static String DB_PASSWORD = "hr";
    private static JdbcRowSet rowSet = null;
    private static RowSetFactory myRowSetFactory = null;

    public static void main(String[] args) {

        try {
//          Method - II : Using the RowSetFactory Interface
//          A factory API that enables applications to 
//          obtain a RowSetFactory implementation that 
//          can be used to create different types of 
//          RowSet implementations.
            myRowSetFactory = RowSetProvider.newFactory();
//            Creates a new instance of a JdbcRowSet.
            rowSet = myRowSetFactory.createJdbcRowSet();
           
            rowSet.setUrl(DB_URL);
            rowSet.setUsername(DB_USERNAME);
            rowSet.setPassword(DB_PASSWORD);
            
            rowSet.setCommand("SELECT first_name,salary FROM temp_emp");
           
            rowSet.setReadOnly(false);
            rowSet.setType(RowSet.TYPE_SCROLL_SENSITIVE);
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            
            rowSet.execute();
            System.out.println("Type :" + rowSet.getType());
            System.out.println("" + rowSet.getConcurrency());
            // RowSet is scrollable and updatable
            // Update a row
            rowSet.last();

            System.out.println("-- Update a row --");
            /*
             int getRow(): Retrieves the current row number. 
             The first row is number 1, 
             */
            System.out.println(rowSet.getRow() + ": "
                    + rowSet.getString("first_name") + ", "
                    + rowSet.getDouble("salary"));
            // update cells
            rowSet.updateDouble("salary", 99.99);
            // update the row in the data source
            rowSet.updateRow();
            System.out.println(rowSet.getRow() + ": "
                    + rowSet.getString("first_name") + ", "
                    + rowSet.getDouble("salary"));

            // Delete a row
            rowSet.first();
            System.out.println("-- Delete a row --");
            System.out.println(rowSet.getRow() + ": "
                    + rowSet.getString("first_name") + ", "
                    + rowSet.getDouble("salary"));
            // delete the current row
            rowSet.deleteRow();

            // A updatable ResultSet has a special row that serves as a staging area
            // for building a row to be inserted.
            rowSet.moveToInsertRow();
            rowSet.updateString(1, "Karan");  // Use column number
            rowSet.updateDouble(2, 77.88);

            rowSet.insertRow();
            rowSet.beforeFirst();

        } catch ( SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rowSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
/*
Connection con = DriverManager.getConnection(
	"jdbc:oracle:thin:@localhost:1521:XE");
Statement stmt = con.createStatement(
        ResultSet.TYPE_SCROLL_SENSITIVE,
	ResultSet.CONCUR_UPDATABLE);

// fetched twenty-five at-a-time from the database
stmt.setFetchSize(25);
ResultSet rs = stmt.executeQuery(
	"SELECT fisrt_name, salary FROM temp_emp");
*/
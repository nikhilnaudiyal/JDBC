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

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import oracle.jdbc.rowset.OracleJDBCRowSet;

/**
 *
 * @author Hrishi
 */
public class JdbcRowSetTest {

    private final static String DB_DRIVER_CLASS = "oracle.jdbc.OracleDriver";
    private final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final static String DB_USERNAME = "hr";
    private final static String DB_PASSWORD = "hr";
    private static JdbcRowSet rowSet = null;

    public static void main(String[] args) throws ClassNotFoundException {

        try {
            Class.forName(DB_DRIVER_CLASS);
//          Method - I : Using the Default Constructor
            rowSet = new JdbcRowSetImpl();
//            rowSet = new OracleJDBCRowSet();
//            Method - II : Using the RowSetFactory Interface
//            RowSetFactory aFactory = RowSetProvider.newFactory();

//            Creates a new instance of a JdbcRowSet.
//            rowSet = aFactory.createJdbcRowSet();
            rowSet.setUrl(DB_URL);
            rowSet.setUsername(DB_USERNAME);
            rowSet.setPassword(DB_PASSWORD);
            rowSet.setCommand("SELECT first_name,salary FROM temp_emp");

            /* Sets the type of this RowSet object to 
            the given type            */
            rowSet.setType(RowSet.TYPE_SCROLL_SENSITIVE);
            /*change the concurrency level of a rowset*/
            rowSet.setConcurrency(RowSet.CONCUR_UPDATABLE);
            rowSet.setReadOnly(false);
            System.out.println("ReadOnly ? :" + rowSet.isReadOnly());
//            Fills this RowSet object with data. 
            rowSet.execute();
            /* 
            Retrieves the type of this ResultSet object
            ResultSet.TYPE_FORWARD_ONLY, 
            ResultSet.TYPE_SCROLL_INSENSITIVE, or 
            ResultSet.TYPE_SCROLL_SENSITIVE
             */
            System.out.println("Type :" + rowSet.getType());
            /* 
            Retrieves the concurrency mode of this ResultSet object.
            ResultSet.CONCUR_READ_ONLY or ResultSet.CONCUR_UPDATABLE
             */
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
            rowSet.updateString(1, "KarunaKaran");  // Use column number
            rowSet.updateDouble(2, 77.88);

            rowSet.insertRow();
            rowSet.beforeFirst();

        } catch (SQLException ex) {
            System.err.println("Error:" + ex.getMessage());
        } finally {
            try {
                rowSet.close();
            } catch (SQLException ex) {
                System.err.println("Error:" + ex.getMessage());
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

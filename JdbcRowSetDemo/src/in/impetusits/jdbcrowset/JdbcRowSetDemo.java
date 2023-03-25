package in.impetusits.jdbcrowset;

import com.sun.rowset.JdbcRowSetImpl;
import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/**
 *
 * @author Hrishi
 */
public class JdbcRowSetDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String username = "HR";
        String password = "hr";
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        RowSetFactory myRowSetFactory = null;
        JdbcRowSet jdbcRs = null;
        try {
            /*
            A factory API that enables applications to 
            obtain a RowSetFactory implementation that 
            can be used to create different types of 
            RowSet implementations. 
            */
            myRowSetFactory = RowSetProvider.newFactory();
            jdbcRs = myRowSetFactory.createJdbcRowSet();
//            jdbcRs = new JdbcRowSetImpl();
            jdbcRs.setUrl(URL);
            jdbcRs.setUsername(username);
            jdbcRs.setPassword(password);
            jdbcRs.setReadOnly(false);
            jdbcRs.setType(RowSet.TYPE_SCROLL_SENSITIVE);
            jdbcRs.setConcurrency(RowSet.CONCUR_UPDATABLE);
           
            jdbcRs.setCommand("select * from EMPLOYEES");
            jdbcRs.execute();
            
              System.out.println("Type :" + jdbcRs.getType());
            System.out.println("" + jdbcRs.getConcurrency());
            while (jdbcRs.next()) {
                System.out.print("First Name :" + jdbcRs.getString("first_name"));
                System.out.println("\tLast Name :" + jdbcRs.getString("last_name"));
            }
            jdbcRs.last();
            jdbcRs.updateString("last_name", jdbcRs.getString("last_name") + "ss");
            jdbcRs.updateRow();
        } catch (SQLException sqle) {
            System.out.println("Error :" + sqle.getMessage());
        } finally {

        }

    }

}

package in.impetusits.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 *
 * @author Hrishi
 */
public class TransactionDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = null;
        Savepoint sp = null;
        try {
            con = DataBase.getConnection();

            //set auto commit to false
            con.setAutoCommit(false);
//            Creates a savepoint with the given 
//            name in the current transaction 
            sp = con.setSavepoint("begin");
            DataBase.insertData(con, "Pranita", 1212.4F);
            DataBase.insertData(con, "Prem", 2112.4F);
            
//          DataBase.insertData(con, "Pran", 1000.4F);
            DataBase.insertData(con, "Jyoti", 10008.4F);

            //now commit transaction
            con.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (con != null) {
                    con.rollback(sp);
                    System.out.println("JDBC Transaction rolled back successfully");
                }
            } catch (SQLException e1) {
                System.out.println("SQLException in rollback" + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}

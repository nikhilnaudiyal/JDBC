/*
 A factory class that uses Oracle Datasource
 */
package in.impetusits.datasource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;
/*
 A factory for connections to the physical 
 data source that this DataSource object 
 represents. 
 An alternative to the DriverManager facility, 
 a DataSource object is the preferred means of 
 getting a connection.
 */
import javax.sql.DataSource;

/**
 *
 * @author Hrishi
 */
public class MyDataSourceFactory {

    public static DataSource getDataSource() {
        /*
         The Properties class represents a persistent 
         set of properties. The Properties can be 
         saved to a stream or loaded from a stream. 
         Each key and its corresponding value in the 
         property list is a string.
         */
        Properties props = new Properties();
        FileInputStream fis = null;
        /*
         Implements  javax.sql.DataSource
         */
        OracleDataSource oracleDS = null;
        try {
            fis = new FileInputStream("datasrc/orcl.properties");
//          fis = new FileInputStream("db.properties");
            /* 
             Reads a property list (key & element 
             pairs) from the input byte stream.
             */
            props.load(fis);
            oracleDS = new OracleDataSource();
            /*
             Set the URL from which connections have 
             to be obtained.
             */
            oracleDS.setURL(props.getProperty("ORACLE_DB_URL"));
            /*
             Set the user name with which connections have 
             to be obtained.
             */
            oracleDS.setUser(props.getProperty("ORACLE_DB_USERNAME"));
            /*
             Set the password with which connections have 
             to be obtained.
             */
            oracleDS.setPassword(props.getProperty("ORACLE_DB_PASSWORD"));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return oracleDS;
    }
}

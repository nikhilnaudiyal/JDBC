package in.impetusits.webrowset;

import java.io.*;
import java.sql.SQLException;
import oracle.jdbc.rowset.OracleWebRowSet;
import javax.sql.RowSet;

/**
 *
 * @author Hrishi
 */

public class WebRowSetDemo {

    //Constant representing the XML file
    private static final String XML_FILE = "wrset.xml";

    public final static void main(String[] args) {
        try {
            //Instantiate a WebRowSet object
            OracleWebRowSet wrs = new OracleWebRowSet();
            //Load driver and set connection parameters
            //  Class.forName("oracle.jdbc.driver.OracleDriver");
            wrs.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
            wrs.setUsername("hr");
            wrs.setPassword("hr");
            //Configure command and execute
            System.out.println("Connecting to data source and "
                    + "generating XML document.");
            String sql = "SELECT first_name,salary FROM temp_emp";
            wrs.setCommand(sql);
           
            wrs.execute();
            //Write XML out to file
            System.out.println("Writing XML to file: " + XML_FILE);
            FileWriter fw = new FileWriter(XML_FILE);
            /*
             Writes the data, properties, and metadata 
             for this WebRowSet object to the given 
             OutputStream object in XML format.
             */
            wrs.writeXml(fw);
            
            wrs.moveToInsertRow();
            wrs.updateString(1, "Manoj");
            wrs.updateString(2, "4500");
            wrs.insertRow();
            wrs.moveToCurrentRow();
            wrs.acceptChanges();
            
            fw.close();
            wrs.close();
            System.out.println("Finished writing XML file.");
        } catch (SQLException | IOException se) {
            System.err.println("Error " + se.getMessage());
        }
        System.out.println("Goodbye!");
    }//end main()
}//end WebRS class

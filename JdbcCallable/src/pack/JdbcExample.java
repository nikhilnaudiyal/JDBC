package pack;

/**
 *
 * @author Hrishi
 */
//STEP 1. Import required packages
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class JdbcExample {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    //"com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:oracle:thin:@localHost:1521:XE";
    //"jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "HR";
    static final String PASS = "hr";

    public static void main(String[] args) {
        Connection conn = null;
        /*
        The interface used to execute SQL stored procedures
        */
        CallableStatement callableStmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "{call query_emp(?, ?, ?)}";
//            Creates a CallableStatement object for 
//            calling database stored procedures
            callableStmt = conn.prepareCall(sql);
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter employee Id :");
            int empID = scan.nextInt();
            //Bind IN parameter first, then bind OUT parameter
            callableStmt.setInt(1, empID); // This would set ID as 102
            // Because second parameter is OUT so register it
            callableStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            callableStmt.registerOutParameter(3, java.sql.Types.DOUBLE);
            //Use execute method to run stored procedure.
            System.out.println("Executing stored procedure...");
            callableStmt.execute();
            //Retrieve employee name with getXXX method
            String empName = callableStmt.getString(2);
            Double salary = callableStmt.getDouble(3);
            /* The procedure in this case return empName
               as unknown when NO_DATA_FOUND 
            */
            if (!empName.equalsIgnoreCase("unknown")) {
                System.out.println(empName + " with ID:"
                        + empID + " earns Rs " + salary);
            } else {
                System.out.println("Employee with Id :" + empID + " was not found!");
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("Error1: " + se.getMessage());
        } catch (ClassNotFoundException ce) {
            //Handle errors for Class.forName
            System.out.println("Error2: " + ce.getMessage());
        } finally {
            //finally block used to close resources
            try {
                if (callableStmt != null) {
                    callableStmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                System.out.println("Error" + se.getMessage());
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample


/*
 -- Procedure using OUT parameter list
 -- Calling a procedure from a PL/SQL Block
 -- Creating local SQL variables and using them as OUT parameters


 CREATE OR REPLACE PROCEDURE query_emp
 (id     IN  employees.employee_id%TYPE,
 name   OUT employees.first_name%TYPE,
 salary OUT employees.salary%TYPE) 	IS
 BEGIN
 SELECT   first_name, salary 
 INTO name, salary
 FROM    employees
 WHERE   employee_id = id;

 EXCEPTION
 WHEN NO_DATA_FOUND THEN
 name := 'unknown';
 salary := 0;
 END query_emp;
 /


 -- plSql Block using the procedure

 SET SERVEROUTPUT  on
 SET VERIFY OFF

 DECLARE
 emp_name employees.first_name%TYPE;
 emp_sal  employees.salary%TYPE;
 emp_id employees.employee_id%TYPE;
 BEGIN
 emp_id := &Employee_No;
 query_emp(emp_id, emp_name, emp_sal);
 DBMS_OUTPUT.PUT_LINE(' Name : ' || emp_name ||
 ' Salary : ' || emp_sal); 

 END;
 /
 */

/*
 query_emp(&emp_id, emp_name, emp_sal);
	
 To use OUT paramenters from the SQL Prompt , local varables needs
 to be created.
 Syntax :
 Variable name varchar2(100);
 Variable salary number;
 Then execute the procedure from the prompt using the local variables whose values are altered by the procedure as they are passed as OUTparamenters
 Execute query_emp(7934, :name, :salary);

 Note - The local variables are prefixed with :Var
 */

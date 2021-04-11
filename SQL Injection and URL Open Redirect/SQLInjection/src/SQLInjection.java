/**
 * Filename: SQLInjection.java
 * Developer: Patrick Walsh
 * Date: 3/29/2021
 * Purpose: Program demonstrates how to execute an SQL Injection attack as
 * well as how to mitigate this attack.
 */

// Import statements
import java.util.Scanner;
import java.sql.*;

 
public class SQLInjection {
	public static void main(String[] args) {
    	
		// login credentials for database
		String userName = "admin";
		String password = "password";
		String dbURL = "jdbc:oracle:thin:@sdev325-db.cfgcjl1rdzsp.us-east-1.rds.amazonaws.com:1521:DATABASE";
		
		
		System.out.println("WELCOME! PLEASE ENTER A USERNAME TO LOGIN TO THE WEBSITE:\n");
		
		
		// Use the Scanner class to input data
		Scanner scannerIn = new Scanner(System.in);
		/*
		 * using the parameterized query uses REPLACE() functions inside the SQL query
		 *  to eliminate single quotes '' which prevents an SQL injection attack.
		 */
	    System.out.println("Use a parameterized query? (y or n): ");
	    String parameterized = scannerIn.nextLine();  // the nextLine() method scans the next string value
	    System.out.println("Username: ");
	    String login_user = scannerIn.nextLine();
	    System.out.println("Password: ");
	    String login_pass = scannerIn.nextLine();
	    
	    scannerIn.close();  // close scanner object
	    
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection conn = DriverManager.getConnection(dbURL, userName, password);
			System.out.println("Connection successful\n");
			
			
			try {
				ResultSet rs;
				String query = null;
				if (parameterized.equals("y")){  // if using parameterized query
					query = "SELECT * FROM Users WHERE username = REPLACE('" + login_user  + "', '''', '''''') AND passwords = REPLACE('" + login_pass + "', '''', '''''')";
				} else if (parameterized.equals("n")){  // if not using parameterized query
					query = "SELECT * FROM Users WHERE username = '" + login_user + "' AND passwords = '" + login_pass + "'";
				}
				
				Statement stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
				// display results of query
				
				while (rs.next()) {
	            	String id = rs.getString(1);
	                String user = rs.getString(2);
	                String pass = rs.getString(3);
	                System.out.println("userID: " + id + "\t" + "username: " + user + "\t\t" + "password: " + pass);
	            }
				
			}  // if failure to execute query
			catch(Exception e) {
				System.out.println("Error: " + e);
			}
			 
			conn.close();  // close SQL connection
			
		}  // if failure to connect to database
		catch(Exception e){
			System.out.println("Error: " + e);
		}
    	
	}  // end of main
}
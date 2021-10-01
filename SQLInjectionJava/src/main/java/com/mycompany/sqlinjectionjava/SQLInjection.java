/*
 * Filename: SQLInjectionJava.java
 * Author: Patrick Walsh
 * Date: 9/29/2021
 * Purpose: Shows example of SQL Injection in Java and
 * how to prevent it using Prepared Statements.
 * Run this query to launch an SQL injection: ' OR '1'='1
 */

package com.mycompany.sqlinjectionjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 *
 * @author pwalsh
 */
public class SQLInjection {
    Connection conn;
 
  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    SQLInjection app = new SQLInjection();
 
    app.connectionToDerby();
  }
 
public void connectionToDerby() throws SQLException, ClassNotFoundException {
    
    Class.forName("org.apache.derby.jdbc.ClientDriver");
    try {
        String dbUrl = "jdbc:derby://localhost:1527/SQLInjectionDB;create=true";
        String user = "username";
        String pass = "password";

        conn = DriverManager.getConnection(dbUrl, user, pass);
        
        
        // Use the Scanner class to input data
        Scanner scannerIn = new Scanner(System.in);
        System.out.println("ENTER USERNAME: ");
        String query_user = scannerIn.nextLine();
            
            
        // VULNERABLE TO SQL INJECTION
        Statement stmt = conn.createStatement();
        String sql_unsafe = "select * from app.users where username = '" + query_user + "'";
        ResultSet rs_unsafe = stmt.executeQuery(sql_unsafe);
        
        // print out query result
        System.out.println("QUERY EXECUTED (UNSAFE): " + sql_unsafe);
        System.out.println("RESULT OF QUERY:");
        while (rs_unsafe.next()) {
            System.out.println("USERID: " + rs_unsafe.getInt("userid"));
            System.out.println("USERNAME: " + rs_unsafe.getString("username"));
        }
        
        
        
        // SAFE FROM SQL INJECTION
        String sql_safe = "select * from app.users where username = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql_safe);
        preparedStmt.setString(1, query_user);
        ResultSet rs_safe = preparedStmt.executeQuery();
        
        
        // print out query result
        System.out.println("\nQUERY EXECUTED (SAFE): " + sql_safe);
        System.out.println("RESULT OF QUERY:");
        while (rs_safe.next()) {
            System.out.println("USERID: " + rs_safe.getInt("userid"));
            System.out.println("USERNAME: " + rs_safe.getString("username"));
        }
        
    } catch (SQLException e){
        System.out.println(e);
    }
}
  
}

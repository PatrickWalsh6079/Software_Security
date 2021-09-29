/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.javasqlinjection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.apache.derby.jdbc.ClientDriver;
 

public class SQLInjection {
  Connection conn;
 
  public static void main(String[] args) throws SQLException {
    SQLInjection app = new SQLInjection();
 
    app.connectionToDerby();
//    app.normalDbUsage();
  }
 
  public void connectionToDerby() throws SQLException {
    // -------------------------------------------
    // URL format is
    // jdbc:derby:<local directory to save data>
    // -------------------------------------------
    //;create=true
    try {
        String dbUrl = "jdbc:derby://localhost:1527/SQLInjectionDB";
        String user = "username";
        String pass = "password";
//        Class.forName("com.mysql.jdbc.Driver");
//        Class.forName("org.apache.derby.jdbc.ClientDriver");
        DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
        conn = DriverManager.getConnection(dbUrl, user, pass);
    } catch (SQLException e){
        System.out.println(e);
    }
    
  }
 
//  public void normalDbUsage() throws SQLException {
//    Statement stmt = conn.createStatement();
// 
//    // drop table
//    // stmt.executeUpdate("Drop Table users");
// 
//    // create table
//    stmt.executeUpdate("Create table users (id int primary key, name varchar(30))");
// 
//    // insert 2 rows
//    stmt.executeUpdate("insert into users values (1,'tom')");
//    stmt.executeUpdate("insert into users values (2,'peter')");
// 
//    // query
//    ResultSet rs = stmt.executeQuery("SELECT * FROM users");
// 
//    // print out query result
//    while (rs.next()) { 
//      System.out.printf("%d\t%s\n", rs.getInt("id"), rs.getString("name"));
//    }
//  }
}
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
//import org.apache.derby.jdbc.ClientDataSource;
//import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author pwalsh
 */
//public class SQLInjection {
//
//    private static final String DB = "jdbc:derby://localhost:1527/SQLInjectionDB;create=true";
//    private static final String USER = "username"; 
//    private static final String PASS = "password";
//    
//    public static void main(String[] args) {
//        
//        
//        
//        try {
//        // login credentials for database
////        String userName = "admin";
////        String password = "password";
////        String dbURL = "jdbc:oracle:thin:@sdev325-db.cfgcjl1rdzsp.us-east-1.rds.amazonaws.com:1521:DATABASE";
////        Class.forName("org.apache.derby.jdbc.ClientDriver");
////        Class.forName("apache_derby_net");
////            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
////            Class.forName("apache_derby_net").newInstance();
//            //Get a connection
////            Connection conn = DriverManager.getConnection(DB, USER, PASS);
//            Connection conn = DriverManager.getConnection(DB);
////        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/SQLinjectionDB","username","password");  
////        Class.forName("oracle.jdbc.OracleDriver");
////	Connection conn = DriverManager.getConnection(dbURL, userName, password);
//            System.out.println("Connection successful\n");
//        
////        try {
////            ClientDataSource ds = new ClientDataSource();
////            ds.setDatabaseName("SDEV425");
////            ds.setServerName("localhost");
////            ds.setPortNumber(1527);
////            ds.setUser("sdev425");
////            ds.setPassword("sdev425");
////            ds.setDataSourceName("jdbc:derby");
////
////            Connection conn = ds.getConnection();
////            
////            // OLD CODE
////            // Vulnerable to SQL injection attack, violating PCI requirement 6.5
//////            Statement stmt = conn.createStatement();
//////            String sql = "select user_id from sdev_users where email = '" + this.username + "'";
//////            ResultSet rs = stmt.executeQuery(sql);
////            // END OF OLD CODE
////            
////            // NEW CODE
////            // Prevents SQL injection attack
////            String sql = "select user_id from sdev_users where email = ?";
////            PreparedStatement stmt = conn.prepareStatement(sql);
////            stmt.setString(1, this.username);
////            ResultSet rs = stmt.executeQuery();
////            // END OF NEW CODE
////            
////            while (rs.next()) {
////                user_id = rs.getInt(1);
////                System.out.println(sql);
////            }
////            if (user_id > 0) {
////                // OLD CODE
//////                // Vulnerable to SQL injection attack, violating PCI requirement 6.5
//////                String sql2 = "select user_id from user_info where user_id = " + user_id + "and password = '" + this.pword + "'";
//////                ResultSet rs2 = stmt.executeQuery(sql2);
////                // END OF OLD CODE
////                
////                // NEW CODE
////                // Prevents SQL injection attack
////                String sql2 = "select user_id from user_info where user_id = ? and password = ?";
////                PreparedStatement stmt2 = conn.prepareStatement(sql2);
////                stmt2.setInt(1, user_id);
////                stmt2.setString(2, this.pword);
////                ResultSet rs2 = stmt2.executeQuery();
////                // END OF NEW CODE
////                
////                while (rs2.next()) {
////                    hitcnt++;
////                }   
////                // Set to true if userid/password match
////               if(hitcnt>0){
////                   status=true;
////               }
////            }
////
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        ///////////////////////////
////        String login_user;
////        try {
////            // Use the Scanner class to input data
////            Scanner scannerIn = new Scanner(System.in);
////            System.out.println("Username: ");
////            login_user = scannerIn.nextLine();
//            // close scanner object
//            
////            ClientDataSource ds = new ClientDataSource();
////            Connection conn = ds.getConnection();
//            
////            Class.forName("com.mysql.jdbc.Driver");
////            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:1527/user", "root", "root");
//            
//            
//
//        // OLD CODE
//            // Vulnerable to SQL injection attack, violating PCI requirement 6.5
//        //            Statement stmt = conn.createStatement();
//        //            String sql = "select user_id from sdev_users where email = '" + this.username + "'";
//        //            ResultSet rs = stmt.executeQuery(sql);
//            // END OF OLD CODE
//
////            // NEW CODE
//////             Prevents SQL injection attack
////            String sql = "select user_id from sdev_users where user = ?";
////            PreparedStatement stmt = conn.prepareStatement(sql);
////            stmt.setString(1, login_user);
////            System.out.println("Query: " + stmt);
//////            ResultSet rs = stmt.executeQuery();
////            // END OF NEW CODE
////        } catch (Exception e) {
////                System.out.println(e);
////        }
//    }
//    
//    
//}

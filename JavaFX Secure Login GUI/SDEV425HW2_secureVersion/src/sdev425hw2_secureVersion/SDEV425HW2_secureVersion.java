//*
/*
 * Author: Patrick Walsh
 * Date: 7/13/2021
 * Purpose: This program provides a simple login GUI using JavaFX along
 * with added security controls taken from NIST.
 */

//package sdev425_2;  // original code
package sdev425hw2_secureVersion;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 * ORIGINAL CODE
 * @author jim Adopted from Oracle's Login Tutorial Application
 * https://docs.oracle.com/javafx/2/get_started/form.htm
 */



//public class SDEV425_2 extends Application { // original code
public class SDEV425HW2_secureVersion extends Application {
    
    // initialize and set the login attempts to 0
    int loginAttempts = 0;
    // specify maximum number of failed logins allowed
    int loginLimit = 5;
    // initialize logger object
    private static final Logger logger = Logger.getLogger(SDEV425HW2_secureVersion.class.getName());
    // initialize datetimestamp in UTC for log
    Instant timestampUTC = Instant.now();
    // set to true to use MFA email verification
    boolean useMFA = true;
    // set to true to check user IP address and hostname whitelist
    boolean useWhitelist = true;
    // whitelists of IP addresses and hostnames
    List<String> ipWhitelist = Arrays.asList("172.31.208.1xxxxxx");
    List<String> hostnameWhitelist = Arrays.asList("PWalsh-E570");
    
    
    @Override
    public void start(Stage primaryStage) {
 
        try {
            
            // get host IP and hostname info for log
            InetAddress address;
            String ip;
            String hostname;
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            hostname = address.getHostName();
            
            // Create a file handler object
            FileHandler handler = new FileHandler("logging.txt");
            handler.setFormatter(new SimpleFormatter());

            // Add file handler as handler of logs
            logger.addHandler(handler);

            // Set Logger level()
            logger.setLevel(Level.FINE);
            // write to log
            logger.log(Level.INFO, "PRIVACY NOTICE MESSSAGE\nHost IP address: "
                    + "{0}\nHostname: {1}\nUTC timestamp: " 
                    + timestampUTC, new Object[]{ip, hostname});

//            System.out.println("start() running");
            primaryStage.setTitle("SDEV425 Login");

            // create a popup window that requires the user to acknowledge the posted
            // security and privacy notices before logging in.
            Stage popupwindow = new Stage();

            popupwindow.initModality(Modality.APPLICATION_MODAL);
            popupwindow.setTitle("PRIVACY NOTICE: CONSENT TO MONITORING");
            Label labelPrivacy= new Label("1.   You are accessing a U.S. Government system;\n" +
            "2. System usage may be monitored, recorded, and subject to audit;\n" +
            "3. Unauthorized use of the system is prohibited and subject to criminal and civil penalties; and\n" +
            "4. Use of the system indicates consent to monitoring and recording;\n" +
            "\nPlease click 'I AGREE' to continue using sytem.");
            Button buttonAgree= new Button("I AGREE");
            buttonAgree.setOnAction(e -> popupwindow.close());
            VBox layout= new VBox(10);

            layout.getChildren().addAll(labelPrivacy, buttonAgree);
            layout.setAlignment(Pos.CENTER);
            Scene popup= new Scene(layout, 550, 200);

            popupwindow.setScene(popup);
            popupwindow.showAndWait();

            logger.log(Level.INFO, "USER ACCEPTED PRIVACY NOTICE MESSSAGE"
                    + "\nHost IP address: "
                    + "{0}\nHostname: {1}\nUTC timestamp: " 
                    + timestampUTC, new Object[]{ip, hostname});
            
            // Grid Pane divides your window into grids
            GridPane grid = new GridPane();
            // Align to Center
            // Note Position is geometric object for alignment
            grid.setAlignment(Pos.CENTER);
            // Set gap between the components
            // Larger numbers mean bigger spaces
            grid.setHgap(10);
            grid.setVgap(10);

            // Create some text to place in the scene
            Text scenetitle = new Text("Welcome. Login to continue.");
            // Add text to grid 0,0 span 2 columns, 1 row
            grid.add(scenetitle, 0, 0, 2, 1);

            // Create Label
            Label userName = new Label("User Name:");
            // Add label to grid 0,1
            grid.add(userName, 0, 1);

            // Create Textfield
            TextField userTextField = new TextField();
            // Add textfield to grid 1,1
            grid.add(userTextField, 1, 1);

            // Create Label
            Label pw = new Label("Password:");
            // Add label to grid 0,2
            grid.add(pw, 0, 2);

            // Create Passwordfield
            PasswordField pwBox = new PasswordField();
            // Add Password field to grid 1,2
            grid.add(pwBox, 1, 2);

            // Create Login Button
            Button btn = new Button("Login");
            // Add button to grid 1,4
            grid.add(btn, 1, 4);

            Text actiontarget = new Text();
            grid.add(actiontarget, 1, 6);
        
            // Set the Action when button is clicked
            btn.setOnAction(new EventHandler<ActionEvent>() {
          
            
            @Override
            public void handle(ActionEvent e) {

                loginAttempts ++;  // increase loginAttempts by 1

                // Log info
                logger.log(Level.INFO, "Login attempt # {0}", loginAttempts +
                        "\nHost IP address: " + ip +
                        "\nHostname: " + hostname + "\nUTC timestamp: " 
                    + timestampUTC);
                // check if max number of logins already attempted
                boolean locked = lockout(loginAttempts, loginLimit);

                // Authenticate the user
                boolean isValid = authenticate(userTextField.getText(), pwBox.getText());
                // If valid clear the grid and Welcome the user
                if (locked){
                    // write to log
                    logger.log(Level.INFO, "Account locked due to too many failed login attempts");
                    GridPane grid3 = new GridPane();
                    // Align to Center
                    // Note Position is geometric object for alignment
                    grid3.setAlignment(Pos.CENTER);
                     // Set gap between the components
                    // Larger numbers mean bigger spaces
                    grid3.setHgap(10);
                    grid3.setVgap(10);
                    Text scenetitle = new Text("Maximum number of failed logins reached! Account locked!");
                    // Add text to grid 0,0 span 2 columns, 1 row
                    grid3.add(scenetitle, 0, 0, 2, 1);
                    Scene scene = new Scene(grid3, 500, 400);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } else if (isValid) {
                    // write to log
                    logger.log(Level.INFO, "Successful login");
                    loginAttempts = 0;  // reset failedAttempts to 0
                    
                    if (useWhitelist == true){  // checks IP address and hostname to make sure they are on whitelist
                        System.out.println("working");
                        if (ipWhitelist.contains(ip) && hostnameWhitelist.contains(hostname)){
//                            System.out.println("contain(ip)");
                        } else {
                            useMFA = false;
                            locked = true;
                            // write to log
                            logger.log(Level.INFO, "Suspicious login: Unknown IP address or hostname\nHost IP address: "
                                    + "{0}\nHostname: {1}\nUTC timestamp: " 
                                    + timestampUTC, new Object[]{ip, hostname});
                            grid.setVisible(false);
                            GridPane grid4 = new GridPane();
                            // Align to Center
                            // Note Position is geometric object for alignment
                            grid4.setAlignment(Pos.CENTER);
                             // Set gap between the components
                            // Larger numbers mean bigger spaces
                            grid4.setHgap(10);
                            grid4.setVgap(10);
                            Text scenetitle = new Text("Suspicious login: Unknown IP address or hostname!");
                            // Add text to grid 0,0 span 2 columns, 1 row
                            grid4.add(scenetitle, 0, 0, 2, 1);
                            Scene scene = new Scene(grid4, 500, 400);
                            primaryStage.setScene(scene);
                            primaryStage.show();
                        }
                    }
                    
                    if (useMFA == true){  // use MFA if turned on (set to true)
                        int verify = twoFactorAuth();  // send verification code through email as MFA
                        System.out.println(verify);
                        
                        grid.setVisible(false);
                        // Grid Pane divides your window into grids
                        GridPane grid3 = new GridPane();
                        // Align to Center
                        // Note Position is geometric object for alignment
                        grid3.setAlignment(Pos.CENTER);
                        // Set gap between the components
                        // Larger numbers mean bigger spaces
                        grid3.setHgap(10);
                        grid3.setVgap(10);

                        // Create Label
                        Label labelVerify = new Label("Verification code:");
                        // Add label to grid 0,1
                        grid3.add(labelVerify, 0, 1);

                        // Create Textfield
                        TextField TextFieldVerify = new TextField();
                        // Add textfield to grid 1,1
                        grid3.add(TextFieldVerify, 1, 1);

                        // Create Login Button
                        Button btnVerify = new Button("VERIFY");
                        // Add button to grid 1,4
                        grid3.add(btnVerify, 1, 4);
                        Scene scene = new Scene(grid3, 300, 200);
                        primaryStage.setScene(scene);
                        primaryStage.show();

                        // Set the Action when button is clicked
                        btnVerify.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent f) {
                                // convert verify to string, then see if it matches
                                // what the user entered.
                                String strVerify = Integer.toString(verify);
                                if (TextFieldVerify.getText().equals(strVerify)){
//                                    System.out.println("correct");
                                    grid3.setVisible(false);
                                    GridPane grid2 = new GridPane();
                                    // Align to Center
                                    // Note Position is geometric object for alignment
                                    grid2.setAlignment(Pos.CENTER);
                                     // Set gap between the components
                                    // Larger numbers mean bigger spaces
                                    grid2.setHgap(10);
                                    grid2.setVgap(10);
                                    Text scenetitle = new Text("Welcome " + userTextField.getText() + "!");
                                    // Add text to grid 0,0 span 2 columns, 1 row
                                    grid2.add(scenetitle, 0, 0, 2, 1);
                                    Scene scene = new Scene(grid2, 500, 400);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();

                                } else {
//                                    System.out.println("wrong");
                                    grid3.setVisible(false);
                                    GridPane grid2 = new GridPane();
                                    // Align to Center
                                    // Note Position is geometric object for alignment
                                    grid2.setAlignment(Pos.CENTER);
                                     // Set gap between the components
                                    // Larger numbers mean bigger spaces
                                    grid2.setHgap(10);
                                    grid2.setVgap(10);
                                    Text scenetitle = new Text("Verification code incorrect!");
                                    // Add text to grid 0,0 span 2 columns, 1 row
                                    grid2.add(scenetitle, 0, 0, 2, 1);
                                    Scene scene = new Scene(grid2, 500, 400);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();
                                }
                            }
                        });
                    } else if (useMFA == false && locked == false){
                        grid.setVisible(false);
                        GridPane grid2 = new GridPane();
                        // Align to Center
                        // Note Position is geometric object for alignment
                        grid2.setAlignment(Pos.CENTER);
                         // Set gap between the components
                        // Larger numbers mean bigger spaces
                        grid2.setHgap(10);
                        grid2.setVgap(10);
                        Text scenetitle = new Text("Welcome " + userTextField.getText() + "!");
                        // Add text to grid 0,0 span 2 columns, 1 row
                        grid2.add(scenetitle, 0, 0, 2, 1);
                        Scene scene = new Scene(grid2, 500, 400);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                    

                   // If Invalid Ask user to try again
                } else {
                    // write to log
                    logger.log(Level.INFO, "Failed to login");
                    Text actiontarget = new Text();
                    grid.add(actiontarget, 1, 5 + loginAttempts);
                    actiontarget.setFill(Color.FIREBRICK);
                    actiontarget.setText("Please try again. Failed login attempts: " + loginAttempts);
                }
                
            }
            
        });
        // Set the size of Scene
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        } catch (IOException | SecurityException messageLog){
            logger.log(Level.WARNING, "..ERROR MESSAGE..{0}", messageLog + 
                    "\nUTC timestamp: " + timestampUTC);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        logger.fine("doing stuff");
        launch(args);
    }

    /**
     * @param user the username entered
     * @param pword the password entered
     * @return isValid true for authenticated
     */
    public boolean authenticate(String user, String pword) {
        System.out.println("authenticate() running");
        boolean isValid = false;
        if (user.equalsIgnoreCase("sdevadmin")
                && pword.equals("425!pass")) {
            isValid = true;
        }

        return isValid;
    }
    
    /**
     * @param login the login attempt number
     * @param limit the login attempt limit
     * @return locked true to lock out account
     */
    public boolean lockout(int login, int limit){
        System.out.println("lockout() running");
        boolean locked = false;
        if (login >= limit){
            locked = true;  // returns locked as true if maximum login attempts have been reached
        }
        
        return locked;
    }
    
    public int twoFactorAuth(){
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session;
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("school.test.email712", "school11!!");
                    }
                });

        try {
 
            // Generates random verification code between 10000 and 99999  
            int min = 10000;
            int max = 99999;
            int verify = (int)(Math.random() * (max - min + 1) + min);
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("school.test.email712@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse("school.test.email712@gmail.com"));
            message.setSubject("Testing Verify account");
            message.setText("Please enter this verification code to prove identity:" +
                            "\n" + verify);

            Transport.send(message);
            
            return verify;
//            System.out.println("Done");

        } catch (MessagingException e) {
                throw new RuntimeException(e);
        }
        
    }
    
}

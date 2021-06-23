/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdev425_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;  // NEW CODE
import java.util.regex.Pattern;  // NEW CODE

/**
 * Filename: SDEV425_1.java
 * @author jim
 * NEW CODE added by: Patrick Walsh
 * Date: 6/23/2021
 * Purpose: Program takes in a command-line input for a file name, then
 * reads the contents of the file and prints the results. Program contains
 * security issue that is fixed in NEW CODE segment to sanitize input.
 */
public class SDEV425_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Read the filename from the command line argument
        String filename = args[0];
        BufferedReader inputStream = null;

        // ************************ NEW CODE ***********************************
//        checks to make sure filename only contains uppercase letters, 
//        lowercase letters, numbers, periods, and underscores
        System.out.println("Reading file " + filename + "...\n");
        Pattern pattern = Pattern.compile("[^A-Za-z0-9._]");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
//            File name contains bad chars; handle error
            System.out.println("Invalid filename! Contains characters other "
                    + "than uppercase letters, lowercase letters, numbers, "
                    + "periods, or underscores");
            System.out.println("\nEXITING PROGRAM....");
            System.exit(0);
        }
        // ************************ END OF NEW CODE ****************************
        String fileLine;
        try {
            inputStream = new BufferedReader(new FileReader(filename));
            System.out.println("Email Addresses:");
            // Read one Line using BufferedReader
            while ((fileLine = inputStream.readLine()) != null) {
                System.out.println(fileLine);
            }
        } catch (IOException io) {
            System.out.println("File IO exception" + io.getMessage());
        } finally {
            // Need another catch for closing 
            // the streams          
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException io) {
                System.out.println("Issue closing the Files" + io.getMessage());
            }

        }
    }

}

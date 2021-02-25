package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class ExternalData {
    static String location = "C:\\Local Bank Accounts"; //This is where all files will be saved.

    /**
     * Checks to see if any accounts exist within the Local Bank Accounts directory.
     *pre: User starts program or chooses to return to menu from another function..
     *post: Returns a true or false value, program continues accordingly.
     */
    public static boolean isDirectoryEmpty(File directory) {
        String[] files = directory.list();
        assert files != null;
        return files.length == 0;
    }


    /**
     * Allows the user to create a client profile with the internal structure on the next line:
     * (name)д(accountNumber)г(password)л(balance)
     *pre: User presses create account button and information in the text fields is valid.
     *post: Creates file with proper internal formatting.
     */
    public static void createClientAccount(int accountNumber, String clientName, String clientPassword, double clientBalance){
        Path path = Paths.get(location, clientName + ".txt");

        try {
            Files.write(path, (clientName + "~" + accountNumber + "`" + clientPassword + "|" + clientBalance + "?").getBytes(),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            //Exception handling.
            System.out.println("An IO error occurred and your file could not be saved.");
        }
    }
}
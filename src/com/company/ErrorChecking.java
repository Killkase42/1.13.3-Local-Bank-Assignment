package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ErrorChecking {

    /**
     * Makes sure the entered name does not include numbers or other characters and limits its length.
     *pre: User is asked to enter their name.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void NameErrorChecking(String variableToCheck, int maxLength){
        if (!Pattern.matches("[a-zA-Z ]+", variableToCheck)) {
            GraphicalDisplays.proceedName = false;
        }
        if (variableToCheck.length() > maxLength) {
            GraphicalDisplays.proceedName = false;
        }
    }

    /**
     * Makes sure the entered name does not already exist as another file.
     *pre: User is asked to enter the client's name.
     *post: User is either allowed to proceed, or receives an error message.
     */
    public static void doesFileExist(String variableToCheck){
        //Checks to make sure a profile with that name does not already exist.
        if (Files.exists(Path.of(ExternalData.location + "\\" + variableToCheck + ".txt"))) {
            GraphicalDisplays.proceedName= false;
        }
    }

    /**
     * Checks to see if information exists within a given file.
     *pre: User starts program or chooses to return to menu from another function..
     *post: Returns a true or false value, program continues accordingly.
     */
    public static void authentication(String fileName, String indexOne, String indexTwo){
        try {
            File myObj = new File(ExternalData.location + "\\" + fileName + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                GraphicalDisplays.passwordCheck = data.substring(data.indexOf(indexOne) + 1, data.indexOf(indexTwo));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }





    }
}
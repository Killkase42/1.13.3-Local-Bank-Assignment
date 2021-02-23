package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphicalDisplays extends javax.swing.JFrame {

    //Create the window (JFrame) to place elements (JPanels) on.
    static JFrame display = new JFrame("National Java Bank");

    /**
     * Constructor
     * pre: none
     * post: Allows the the object "data" to be executed. Used in making a method a parameter of another method.
     */
    public interface Command {
        void execute(Object data);
    }

    /**
     * Inputs a button of specified parameters and function.
     * pre: User clicks button.
     * post: Desired command is run.
     */
    public static void button(int xPosition, int yPosition, int width, int height, String buttonText, String method) {
        JButton button = new JButton(buttonText);
        button.setFont(button.getFont().deriveFont((float) (width / 10))); //Makes sure the text on the button is the right
        //size.
        button.setBounds(xPosition, yPosition, width, height);
        display.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //What happens when the button is pressed.
                if (method.equals("logInClient")) {
                    logInClient();
                } else if (method.equals("mainMenu")) {
                    try {
                        mainMenu();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (method.equals("createAccount")) {
                    createAccount();
                } else if (method.equals("viewReport")) {
                    viewReport();
                } else if (method.equals("createAccountPush")) {
                    createAccountPush();
                } else if (method.equals("viewClientInformation")) {
                    try {
                        logInClientPush();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * Gets rid of all previous objects on the screen.
     * pre: Button to open new screen is pressed.
     * post: All previous elements on the screen are cleared..
     */
    public static void refreshScreen() {
        display.getContentPane().removeAll();
        display.revalidate();
        display.repaint();
    }

    /**
     * Displays text.
     * pre: none
     * post: Chosen text is displayed.
     */
    public static void text(int xPosition, int yPosition, int width, int height, int fontSize, String text) {
        JLabel label;
        label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(fontSize + .0f));
        label.setBounds(xPosition, yPosition, width, height);
        display.add(label);
    }

    /**
     * Displays images from url.
     * pre: none
     * post: Chosen image is displayed.
     */
    public static void image(int xPosition, int yPosition, int width, int height, String imageURL) throws IOException {
        URL url = new URL(imageURL);
        BufferedImage image = ImageIO.read(url);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(xPosition, yPosition, width, height);
        display.add(label);
    }

    /**
     * Displays a text field that the user can write into.
     * pre: none
     * post: Chosen image is displayed.
     */
    public static void textField(int xPosition, int yPosition, int width, int height, String textVar) {
        JTextField field;
        field = new JTextField();
        field.setBounds(xPosition, yPosition, width, height);
        display.add(field);
    }

    /**
     * Checkbox--------------------------------------------------------------------------------------------------------------
     * pre: User presses "Log in Client" button.
     * post: User is prompted to enter the log in information of a client at the bank.
     */

    static Boolean condition = false; //Universal boolean condition used throughout all checkboxes.
    static final JLabel checkLabel = new JLabel();

    public static void checkBox(int xPosition, int yPosition, int width, int height, String displayedText) {
        JCheckBox checkbox1 = new JCheckBox(displayedText);
        checkbox1.setBounds(xPosition, yPosition, width, height);
        display.add(checkbox1);
        display.add(checkLabel);
        checkbox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                checkLabel.setText(displayedText
                        + (e.getStateChange() == 1 ? "checked" : "unchecked"));
                if (condition) {
                    condition = false;
                } else {
                    condition = true;
                }
            }
        });
    }

    /**
     * Displays the main menu.
     * pre: none
     * post: User is given multiple options to choose from to progress program.
     */
    public static void mainMenu() throws IOException {
        refreshScreen();

        //Resets variables used in account creation so error messages do not show up upon method being run.
        errorName = false;
        errorPass = false;
        errorName1 = false;
        errorPass1 = false;
        accountCreated = false;

        image(-200, -50, 1000, 500,
                "https://raw.githubusercontent.com/Killkase42/1.13.3-Local-Bank-Assignment/master/Images/nationalBankofJava.png");
        text(570, 17, 1000, 400, 130, "National Bank");
        button(116, 525, 350, 175, "Log In Client", "logInClient");
        button(566, 525, 350, 175, "Create Account", "createAccount");
        button(1016, 525, 350, 175, "View Report", "viewReport");
    }

    //The Jlable text field objects that will intake data from the user.
    static JTextField field10;
    static JTextField field11;
    //Variables used to store data across the createAccount and createAccountPush methods.
    static String enteredFullName1 = "";
    static String enteredPassword1 = "";
    //Variables used to display error messages.
    static Boolean errorName1 = false;
    static Boolean errorPass1 = false;
    /**
     * Displays an interface in which the user is prompted to input a client's information to later modify it.
     * pre: User presses "Log in Client" button.
     * post: User is prompted to enter the log in information of a client at the bank.
     */
    public static void logInClient() {
        refreshScreen();

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        button(593, 455, 300, 75, "Log in", "viewClientInformation");

        text(525, 195, 1000, 200, 75, "Log in Client");
        text(530, 254, 500, 200, 25, "Input information for the client below.");
        text(560, 285, 500, 200, 25, "Full Name:");
        text(560, 330, 500, 200, 25, "Password:");


        //Individual text fields, these are not using the method since unique information is required from each.
        field10 = new JTextField();
        field10.setBounds(690, 371, 200, 30);
        display.add(field10);
        field11 = new JTextField();
        field11.setBounds(690, 416, 200, 30);
        display.add(field11);

        //Two if statements below display error message for name and/or password based on variable changes from following
        //error methods.
        if (errorName1) {
            text(890, 358, 400, 50, 15, "* Account not Found");
        }
        if (errorPass1) {
            text(890, 398, 400, 50, 15, "* Incorrect Password");
        }
    }

    static boolean proceedName1 = true;
    static boolean proceedPass1 = true;
    static String passwordCheck = "";
    /**
     * Confirms whether or not the entered client information is valid or not and logs in client if it is.
     * pre: User presses "Log in Client" button.
     * post: Logged-in-client's information is displayed.
     */
    public static void logInClientPush() throws FileNotFoundException {
        refreshScreen();

        //Resetting variables.
        proceedName = true;
        proceedPass = true;
        boolean proceedWithChecking = true;

        //Variables that will become what the user enters in the text boxes.
        enteredFullName1 = field10.getText();
        enteredPassword1 = field11.getText();

        //Checking to see if the entered name and password are within acceptable parameters.
        if (enteredFullName1.equals("")){
            proceedName = false;
        }
        ErrorChecking.NameErrorChecking(enteredFullName1, 25);
        if (proceedName) {
            ErrorChecking.doesFileExist(enteredFullName1);
            if (proceedName){
                proceedName = false;
            } else if (!proceedName){
                proceedName = true;
            }
        }

        if (proceedName){
            ErrorChecking.authentication(enteredFullName1, "`", "|");
        }
        if (!enteredPassword1.equals(passwordCheck)) {
            proceedPass = false;
        }

        //Three if statements below serve tp return the user to the start of account creation if their input is invalid.
        if (!proceedName) {
            proceedWithChecking = false;
            errorName1 = true;
        }
        if (!proceedPass) {
            proceedWithChecking = false;
            errorPass1 = true;
        }
        if (!proceedName || !proceedPass) {
            logInClient();
        } else if (proceedName && proceedPass) {
            proceedWithChecking = true;
        }

        //Leads to the file being created.
        if (proceedWithChecking) {
            clientModifyScreen();
        }

    }

    /**
     * Displays an interface in which the logged-in-client's information is displayed. Deposits and withdrawals are made
     * here.
     * pre: User presses "Log in Client" button.
     * post: Logged-in-client's information is displayed.
     */
    public static void clientModifyScreen(){
        refreshScreen();

        ErrorChecking.authentication(enteredFullName1, "|", "?");

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        text(510, 160, 1000, 200, 30, "Logged in:");
        text(670, 160, 1000, 200, 30, enteredFullName1);
        text(510, 200, 1000, 200, 30, "Account Balance: $");
        text(790, 200, 1000, 200, 30, passwordCheck);

        button(530, 315, 450, 60, "Deposit/Withdrawal", "depositOrWithdrawal");

    }

    /**
     * Allows the user to take money out of or put money into the client's account.
     * pre: User presses "Deposit/Withdrawal" button.
     * post: Logged-in-client's information is displayed.
     */
    public static void depositOrWithdrawal(){
        refreshScreen();

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        //Checkboxes for either option

    }

    //The Jlable text field objects that will intake data from the user.
    static JTextField field;
    static JTextField field1;
    static JTextField field2;
    //Variables used to store data across the createAccount and createAccountPush methods.
    static String enteredFullName = "";
    static String enteredPassword = "";
    static String enteredPasswordConfirmation = "";
    //Variables used to display error messages.
    static Boolean errorName = false;
    static Boolean errorPass = false;
    static Boolean accountCreated = false; //Serves to show whether or not an account was succesfully created.

    /**
     * Displays an interface in which the user is prompted to create an account.
     * pre: User presses "Create Account" button.
     * post: User prompted to enter information for a new client account.
     */
    public static void createAccount() {
        refreshScreen();

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        button(593, 455, 300, 75, "Create Account", "createAccountPush");

        text(460, 160, 1000, 200, 75, "Create Account");
        text(530, 209, 500, 200, 25, "Input information for the client below.");
        text(560, 240, 500, 200, 25, "Full Name:");
        text(476, 285, 500, 200, 25, "Create Password:");
        text(462, 330, 500, 200, 25, "Confirm Password:");

        //Individual text fields, these are not using the method since unique information is required from each.
        field = new JTextField();
        field.setBounds(690, 326, 200, 30);
        display.add(field);
        field1 = new JTextField();
        field1.setBounds(690, 371, 200, 30);
        display.add(field1);
        field2 = new JTextField();
        field2.setBounds(690, 416, 200, 30);
        display.add(field2);

        //Two if statements below display error message for name and/or password based on variable changes from following
        //error methods.
        if (errorName) {
            text(890, 308, 400, 50, 15, "* Name must be unique among other");
            text(898, 325, 400, 50, 15, "accounts and only include letters.");
        }
        if (errorPass) {
            text(890, 358, 400, 50, 15, "* Password must include match and");
            text(900, 375, 400, 50, 15, "include at least one character.");
        }
        if (accountCreated) {
            text(610, 515, 300, 50, 20, "Account created successfully!");
        }
    }

    static boolean proceedName = true;
    static boolean proceedPass = true;
    /**
     * Creates an account and adds it to the file
     * pre: User presses "Create Account" button.
     * post: Information is uploaded to .txt file in proper format.
     */
    public static void createAccountPush() {
        //Resetting variables.
        proceedName = true;
        proceedPass = true;
        boolean proceedWithChecking = true;

        //Variables that will become what the user enters in the text boxes.
        enteredFullName = field.getText();
        enteredPassword = field1.getText();
        enteredPasswordConfirmation = field2.getText();

        //Checking to see if the entered name, password and password confirmation are within acceptable parameters.
        ErrorChecking.NameErrorChecking(enteredFullName, 25);
        if (proceedName) {
            ErrorChecking.doesFileExist(enteredFullName);
        }

        if (!enteredPassword.equals(enteredPasswordConfirmation) || enteredPassword.equals("")) {
            proceedPass = false;
        }

        //Three if statements below serve tp return the user to the start of account creation if their input is invalid.
        if (!proceedName) {
            proceedWithChecking = false;
            errorName = true;
        }
        if (!proceedPass) {
            proceedWithChecking = false;
            errorPass = true;
        }
        if (!proceedName || !proceedPass) {
            accountCreated = false;
            createAccount();
        } else if (proceedName && proceedPass) {
            proceedWithChecking = true;
        }

        //Leads to the file being created.
        if (proceedWithChecking) {
            allGoodMessage();
        }
    }

    /**
     * Method that actually goes through in creating the client's file.
     * pre: User presses "Create Account" button (when all entered client information is valid).
     * post: Appropriate message is displayed.
     */
    public static void allGoodMessage() { //Displays that profile was successfully created.
        display.repaint();
        int accountNumber = new Random().nextInt(1000000); //Obtain a number between [0 - 1000000].
        ExternalData.createClientAccount(accountNumber, enteredFullName, enteredPassword, 0); //Save account.
        field.setText("");
        field1.setText("");
        field2.setText("");
        accountCreated = true;
        errorName = false;
        errorPass = false;
        createAccount();
    }

    /**
     * Displays an interface in which every account's information is displayed in a table.
     * pre: User presses "Create Account" button.
     * post: User prompted to enter information for a new client account.
     */
    public static void viewReport() {
        refreshScreen();

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");
    }

    /**
     * Main method.
     * pre: none
     * post: mainMenu method is run.
     */
    public static void main(String[] args) throws IOException {
        display.setLayout(null);

        //If the user has not already run the code, this statement creates a directory that client accounts will be
        //stored in.
        if (Files.notExists(Path.of(ExternalData.location))) {
            new File(ExternalData.location).mkdir();
        }

        mainMenu(); //Start by displaying the main menu.

        //Setting attributes of the window.
        int screenWidth = 1500;
        int screenHeight = 1000;
        display.pack();
        display.setLocation(200, 25);
        display.setSize(screenWidth, screenHeight);
        display.setLayout(null);
        display.setVisible(true);
    }
}
package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import javax.swing.*;

import static com.company.GDF.*;

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
        amountError = false;
        idError= false;
        choiceError= false;

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
    static JTextField field12;
    static JTextField field13;
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
        field12 = new JTextField();
        field13 = new JTextField();
        field12.setText("");
        field13.setText("");

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

    static double clientBalance = 0.00;
    static int clientID = 0;
    /**
     * Displays an interface in which the logged-in-client's information is displayed. Deposits and withdrawals are made
     * here.
     * pre: User presses "Log in Client" button.
     * post: Logged-in-client's information is displayed.
     */
    public static void clientModifyScreen(){
        refreshScreen();

        //Getting the client's balance and ID from their file.
        ErrorChecking.authentication(enteredFullName1, "|", "?");
        clientBalance = Double.parseDouble(passwordCheck);
        ErrorChecking.authentication(enteredFullName1, "~","`");
        clientID = Integer.parseInt(passwordCheck);

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        text(510, 160, 1000, 200, 30, "Logged in:");
        text(670, 160, 1000, 200, 30, enteredFullName1);
        text(510, 200, 1000, 200, 30, "Account Balance: $");
        text(790, 200, 1000, 200, 30, String.valueOf(clientBalance));

        button(530, 315, 450, 60, "Deposit/Withdrawal", "depositOrWithdrawal");
    }

    static boolean makeDeposit = false;
    static boolean makeWithdrawal = false;
    static double enteredAmount = 0.00;
    static int enteredID = 0;
    static double newBalance = 0.00;
    static boolean amountError = false;
    static boolean idError= false;
    static boolean choiceError= false;
    /**
     * Allows the user to take money out of or put money into the client's account.
     * pre: User presses "Deposit/Withdrawal" button.
     * post: Logged-in-client's information is displayed.
     */
    public static void depositOrWithdrawal(){
        refreshScreen();

        //Resetting variables.
        makeDeposit = false;
        makeWithdrawal = false;

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        button(510, 500, 540, 60, "Commit to File", "completeTransaction");

        text(510, 160, 1000, 200, 30, "Logged in:");
        text(670, 160, 1000, 200, 30, enteredFullName1);
        text(510, 200, 1000, 200, 30, "Account Balance: $");
        text(790, 200, 1000, 200, 30, String.valueOf(clientBalance));
        text(510, 250, 1000, 200, 30, "Select which one you want to make:");
        text(510, 330, 1000, 200, 30, "Enter desired amount:");
        text(510, 365, 1000, 200, 30, "Enter ID for authentication:");
        field12.setBounds(835, 420, 180, 30);
        field13.setBounds(900, 455, 115, 30);
        display.add(field12);
        display.add(field13);
        field12.setText("");
        field13.setText("");

        //Placing the deposit/withdrawal checkboxes.
        JCheckBox checkbox1 = new JCheckBox("Deposit");
        JCheckBox checkbox2 = new JCheckBox("Withdrawal");
        checkbox1.setBounds(750, 370, 100, 50);
        checkbox2.setBounds(600, 370, 100, 50);
        display.add(checkbox1);
        display.add(checkbox2);
        display.add(checkLabel);

        //Adding functionality to the checkboxes to prevent both from being selected. Also, changes a variable to either
        //deposit or select money if they are clicked.
        checkbox1.addItemListener(e -> {
            checkLabel.setText("Deposit"
                    + (e.getStateChange() == 1 ? "checked" : "unchecked"));
            if (checkbox1.isSelected()) {
                checkbox2.setEnabled(false);
                makeDeposit = true;
            } else if (!checkbox1.isSelected()){
                checkbox2.setEnabled(true);
                makeDeposit = false;
            }
        });
        checkbox2.addItemListener(e -> {
            checkLabel.setText("Withdrawal"
                    + (e.getStateChange() == 1 ? "checked" : "unchecked"));
            if (checkbox2.isSelected()) {
                checkbox1.setEnabled(false);
                makeWithdrawal = true;
            } else if (!checkbox2.isSelected()){
                checkbox1.setEnabled(true);
                makeWithdrawal = false;
            }
        });

        if (amountError){
            field12.setText("");
            text(1050, 330, 1000, 200, 15, "* Invalid number.");
        }
        if (idError){
            field13.setText("");
            text(1050, 370, 1000, 200, 15, "* Incorrect ID.");
        }
        if (choiceError){
            text(850, 295, 1000, 200, 15, "* You must pick one.");
        }
    }

    /**
     * Modifies the client's balance (saves to file).
     * pre: User presses "Complete Transaction" button.
     * post: Updated client balance is saved to their file.
     */
    public static void completeTransaction(){
        boolean proceed = true;
        enteredAmount = 0.00;
        enteredID = 0;
        amountError = false;
        idError = false;

        try {
            enteredAmount = Double.parseDouble(field12.getText());
            enteredAmount = Math.round(enteredAmount * 100.00) / 100.00;
            if (makeWithdrawal){
                if ((clientBalance - enteredAmount) < 0) {
                    proceed = false;
                    amountError = true;
                }
            }
        } catch (Exception e){
            amountError = true;
            proceed = false;
        }
        try {
            enteredID = Integer.parseInt(field13.getText());
        } catch (Exception e){
            idError = true;
            proceed = false;
        }



        if (clientID != enteredID){
            proceed = false;
            idError = true;
        }
        if (!makeDeposit && !makeWithdrawal){
        proceed = false;
        choiceError = true;
        } else {
            choiceError = false;
        }
        if (proceed){
            if (makeDeposit && !makeWithdrawal){
                clientBalance += enteredAmount;
            } else if (!makeDeposit){
                clientBalance -= enteredAmount;
            }

            clientBalance = Math.round(clientBalance * 100.00) / 100.00;
            ExternalData.createClientAccount(clientID, enteredFullName1, enteredPassword1, clientBalance);
            clientModifyScreen();
        } else {
            depositOrWithdrawal();
        }
        enteredAmount = 0.00;
        enteredID = 0;

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
    static Boolean accountCreated = false; //Serves to show whether or not an account was successfully created.

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
            text(890, 358, 400, 50, 15, "* Passwords must match and");
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
        ExternalData.createClientAccount(accountNumber, enteredFullName, enteredPassword, 0.00); //Save account.
        field.setText("");
        field1.setText("");
        field2.setText("");
        accountCreated = true;
        errorName = false;
        errorPass = false;
        createAccount();
    }



    //Make a SPECIFIC button method because it will be placed many times in for loop iteration.
    public static void buttonSPECIFIC(int xPosition, int yPosition, int width, int height, String buttonText,
                                      String method, String nameDelete) {
        JButton button1 = new JButton(buttonText);
        button1.setFont(button1.getFont().deriveFont((float) (25))); //Makes sure the text on the button is the right
        //size.
        button1.setBounds(xPosition, yPosition, width, height);
        display.add(button1);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //What happens when the button is pressed.
                if (method.equals("deleteAccount")){
                    deleteAccount(nameDelete);
                }
            } //Runs certain method based on button clicked.
        });
    }

    static File dir = new File(ExternalData.location); // current directory
    static int arraylistRow = 0;
    static String[][] table = new String[4][4]; //Arraylist to store information found in each client's file.
    static int currentDirListing = 0;

    public static void resetForReportINITIAL(){
        arraylistRow = 0;
        currentDirListing = 0;
        viewReportINITIAL();
    }

    /**
     * Puts the information found in client files into an arraylist using recursion.
     * pre: User presses "View Report" button.
     * post: Client files are searched and their information is arranged in an arraylist.
     */
    public static void viewReportINITIAL() {
        refreshScreen();

        File[] directoryListing = dir.listFiles();


        if (directoryListing != null) {
            try
            {
                arraylistRow ++;
                String wholeFileName = String.valueOf(directoryListing[currentDirListing]);
                String fileName = wholeFileName.substring(wholeFileName.indexOf("\\",
                        wholeFileName.indexOf("\\") + 1) + 1, wholeFileName.indexOf("."));

                //Assigning data within the file to the arraylist.
                table[arraylistRow][1] = fileName;
                ErrorChecking.authentication(fileName, "~","`");
                table[arraylistRow][2] = passwordCheck;
                ErrorChecking.authentication(fileName, "|","?");
                table[arraylistRow][3] = passwordCheck;

                currentDirListing ++;
                viewReportINITIAL();
            }catch (Exception e){

                viewReport();
            }
        }
    }

    /**
     * Displays an interface in which every account's information is displayed in a table.
     * pre: User presses "Create Account" button.
     * post: User prompted to enter information for a new client account.
     */
    public static void viewReport() {
        refreshScreen();

        button(25, 25, 150, 50, "Back to Menu", "mainMenu");

        text(400, 100,1000,100,64,"Client Account Report");
        text(250, 200,300,40,35,"Client Name:");
        text(650, 200,300,40,35,"Client ID:");
        text(1050, 200,300,40,35,"Balance:");

        //Actually displaying client information and delete account buttons.
        try {
            for (int i = 1; i < arraylistRow; i++) {
                text(250, i*60+200, 300, 40, 35, table[i][1]);
                text(650, i*60+200, 300, 40, 35, table[i][2]);
                text(1050, i*60+200, 150, 40, 35, table[i][3]);
                text(1025, i*60+200, 50, 40, 35, "$");
                buttonSPECIFIC(1200, i*60+200, 250, 40, "Delete Account",
                        "deleteAccount", table[i][1]);
            }

        } catch (Exception ignored){
        }
        if ((table[1][1]) == null){ //If there are no accounts, this is printed when on the client report.
            text(200, 275,1500,40,35,
                    "-----------------------//No Client Accounts Found!//-----------------------");
        }
    }

    /**
     * Serves to delete a file then redisplay the client report.
     * pre: User presses one of the "Delete Account" buttons.
     * post: Specific acount is deleted and viewReportINITIAL method is run again to redisplay.
     */
    public static void deleteAccount(String nameDelete){

        File toDelete = new File(ExternalData.location + "\\" + nameDelete + ".txt");
        toDelete.delete();
        resetForReportINITIAL();
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
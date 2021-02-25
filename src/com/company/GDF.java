package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class GDF { //GDF is for GraphicalDisplayFeatures.

    /**
     * Inputs a button of specified parameters and function.
     * pre: User clicks button.
     * post: Desired command is run.
     */
    public static void button(int xPosition, int yPosition, int width, int height, String buttonText, String method) {
        JButton button = new JButton(buttonText);
        button.setFont(button.getFont().deriveFont((float) (width / 10))); //Makes sure the text on the button is the
        //right size.
        button.setBounds(xPosition, yPosition, width, height);
        GraphicalDisplays.display.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { //What happens when the button is pressed.
                switch (method) {
                    case "logInClient":  //Runs certain method based on button clicked.
                        GraphicalDisplays.logInClient();
                        break;
                    case "mainMenu":
                        try {
                            GraphicalDisplays.mainMenu();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        break;
                    case "createAccount":
                        GraphicalDisplays.createAccount();
                        break;
                    case "viewReport":
                        GraphicalDisplays.resetForReportINITIAL();
                        break;
                    case "createAccountPush":
                        GraphicalDisplays.createAccountPush();
                        break;
                    case "viewClientInformation":
                        try {
                            GraphicalDisplays.logInClientPush();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        break;
                    case "depositOrWithdrawal":
                        GraphicalDisplays.depositOrWithdrawal();
                        break;
                    case "completeTransaction":
                        GraphicalDisplays.completeTransaction();
                        break;
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
        GraphicalDisplays.display.getContentPane().removeAll();
        GraphicalDisplays.display.revalidate();
        GraphicalDisplays.display.repaint();
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
        GraphicalDisplays.display.add(label);
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
        GraphicalDisplays.display.add(label);
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
        GraphicalDisplays.display.add(field);
    }

    static Boolean condition = false; //Universal boolean condition used throughout all checkboxes.
    static final JLabel checkLabel = new JLabel();
    /**
     * Produces a clickable checkbox on the screen.
     * pre: none
     * post: User may click a checkbox to change a boolean value.
     */
    public static void checkBox(int xPosition, int yPosition, int width, int height, String displayedText) {
        JCheckBox checkbox1 = new JCheckBox(displayedText);
        checkbox1.setBounds(xPosition, yPosition, width, height);
        GraphicalDisplays.display.add(checkbox1);
        GraphicalDisplays.display.add(checkLabel);
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
}

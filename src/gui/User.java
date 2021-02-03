package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class creates UI for handling user sign up, login and change username ability.
 * It has separate methods for each of these abilities.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */
public class User {
    //button to rename user
    private final JButton renameButton;

    //text field to get username
    private final JTextField newUserField;

    //text field to get new username
    private final JTextField renameUserField;

    //user background
    private Background userBg;

    //background for rename frame
    private Background renameBg;

    //frame for login/ sign up
    private final JFrame userFrame;

    //frame for renaming
    private final JFrame renameFrame;

    //background color for buttons
    private final Color bgColor;

    //foreground color for buttons
    private final Color fgColor;

    //new font
    private final Font font;

    //login button
    private final JButton loginButton;

    //sign up button
    private final JButton signUpButton;

    //create background images and handle possible exception
    {
        try {
            userBg = new Background(".\\Extras\\new_user.jpeg");
            renameBg = new Background(".\\Extras\\rename_user.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets user (login/sign) up frame
     * @return user frame
     */
    public JFrame getUserFrame() {
        return userFrame;
    }

    /**
     * constructor to create needed objects
     */
    public User(){

        //rename button
        renameButton = new JButton("Rename");

        //sign up/ login frame
        userFrame = new JFrame("New User");

        //rename user frame
        renameFrame = new JFrame("Rename User");

        //background color
        bgColor = new Color(102,102,102);
        //foreground color
        fgColor = new Color(0,102,0);

        //user text field
        newUserField = new JTextField();

        //new user field
        renameUserField = new JTextField();

        //new field
        font = new Font("new",Font.PLAIN,18);

        //login button
        loginButton = new JButton("Login");

        //sign up button
        signUpButton = new JButton("Sign Up");
    }

    /**
     * This method creates login/sign up frame
     * also sets component features
     */
    public void createUserFrame(){
        //create frame using createFrame method
        createFrames(userFrame);

        //set user frame content to user background
        userFrame.setContentPane(userBg);

        //set background layout to null
        userBg.setLayout(null);

        //manually set new user field bounds
        newUserField.setBounds(37,135,328,52);

        //manually set login button bounds
        loginButton.setBounds(40,210,160,30);

        //manually set sign up button bounds
        signUpButton.setBounds(200,210,160,30);

        //set button features
        makeButton(loginButton);
        makeButton(signUpButton);

        //add all
        userBg.add(newUserField);
        userBg.add(loginButton);
        userBg.add(signUpButton);

        //show frame
        userFrame.setVisible(true);

    }

    /**
     * This method creates UI for when player wants to change username
     */
    public void renameUserFrame(){
        //create the frame
        createFrames(renameFrame);

        //set content to rename background
        renameFrame.setContentPane(renameBg);

        //set layout to null
        renameBg.setLayout(null);

        //manually set bounds for field and button
        renameUserField.setBounds(37,135,328,52);
        renameButton.setBounds(40,210,320,35);

        //set rename button features
        makeButton(renameButton);

        //add all
        renameBg.add(renameUserField);
        renameBg.add(renameButton);

        //show frame
        renameFrame.setVisible(true);

    }

    /**
     * This method gets a button and sets features such as color and font
     * @param button given button
     */
    public void makeButton(JButton button){
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
    }

    /**
     * This method gets a frame and sets its features
     * @param frame given frame
     */
    public void createFrames(JFrame frame){
        frame.setLayout(null);
        frame.setLocation(450,250);
        frame.setSize(400,280);
        frame.setResizable(false);
    }

    /**
     * gets rename button
     * @return rename button field
     */
    public JButton getRenameButton() {
        return renameButton;
    }

    /**
     * gets rename user field
     * @return rename user field
     */
    public JTextField getRenameUserField() {
        return renameUserField;
    }

    /**
     * gets new user field
     * @return new user field
     */
    public JTextField getNewUserField() {
        return newUserField;
    }

    /**
     * gets login button from first frame
     * @return login button
     */
    public JButton getLoginButton() {
        return loginButton;
    }
    /**
     * gets sign up button
     * @return sign up button field
     */
    public JButton getSignUpButton() {
        return signUpButton;
    }

    /**
     * gets rename frame
     * @return rename frame field
     */
    public JFrame getRenameFrame() {
        return renameFrame;
    }
}

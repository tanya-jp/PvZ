package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class User {
    private final JButton createButton;
    private final JButton renameButton;

    private final JTextField newUserField;
    private final JTextField renameUserField;

    private Background userBg;
    private Background renameBg;

    private final JFrame userFrame;
    private final JFrame renameFrame;

    private final Color bgColor;
    private final Color fgColor;

    private final Font font;

    //TODO:add new buttons
    private JButton loginButton;
    private JButton signUpButton;

    {
        try {
            userBg = new Background(".\\Extras\\new_user.jpeg");
            renameBg = new Background(".\\Extras\\rename_user.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JFrame getUserFrame() {
        return userFrame;
    }

    public User(){
        //create buttons
        createButton = new JButton("OK");
        renameButton = new JButton("Rename");

        userFrame = new JFrame("New User");
        renameFrame = new JFrame("Rename User");

        bgColor = new Color(102,102,102);
        fgColor = new Color(0,102,0);

        newUserField = new JTextField();
        renameUserField = new JTextField();

        font = new Font("new",Font.PLAIN,18);

        //TODO:
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
    }

    public void createUserFrame(){
        createFrames(userFrame);
        userFrame.setContentPane(userBg);

        userBg.setLayout(null);

        newUserField.setBounds(37,135,328,52);

        createButton.setBounds(40,210,320,30);
        makeButton(createButton);

        //TODO:
        loginButton.setBounds(40,210,160,30);
        signUpButton.setBounds(200,210,160,30);
        makeButton(loginButton);
        makeButton(signUpButton);

        userBg.add(newUserField);
        userBg.add(loginButton);
        userBg.add(signUpButton);
//        userBg.add(createButton);
        userFrame.setVisible(true);

    }

    public void renameUserFrame(){
        createFrames(renameFrame);
        renameFrame.setContentPane(renameBg);

        renameBg.setLayout(null);

        renameUserField.setBounds(37,135,328,52);

        renameButton.setBounds(40,210,320,35);
        makeButton(renameButton);

        renameBg.add(renameUserField);
        renameBg.add(renameButton);
        renameFrame.setVisible(true);

    }

    public void makeButton(JButton button){
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
    }

    public void createFrames(JFrame frame){
        frame.setLayout(null);
        frame.setLocation(450,250);
        frame.setSize(400,280);
        frame.setResizable(false);
    }


    public JButton getRenameButton() {
        return renameButton;
    }

    public JTextField getRenameUserField() {
        return renameUserField;
    }

    public JTextField getNewUserField() {
        return newUserField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
    public JButton getSignUpButton() {
        return signUpButton;
    }
    //TODO:
    public JFrame getRenameFrame() {
        return renameFrame;
    }
}

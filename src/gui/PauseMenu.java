package gui;

import javafx.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class PauseMenu {
    //buttons
    private JButton resumeButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton cancelButton;
    private JButton leaveButton;
    //back ground pics
    private Background pauseBg;
    private Background askBg;
    //frames
    private JFrame pauseFrame;
    private JFrame askFrame;

    //colors
    private Color bgColor;//background color
    private Color fgColor;//foreground color


    private Font font;

    {
        try {
            pauseBg = new Background(".\\Extras\\pause_menu.jpeg");
            askBg = new Background(".\\Extras\\ask_user1.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PauseMenu(){
        resumeButton = new JButton("Back To Game");
        saveButton = new JButton("Save Your Game");
        exitButton = new JButton("Go To Main Menu");//will also save the game
        cancelButton = new JButton("Cancel");
        leaveButton = new JButton("Leave");
        //frames
        pauseFrame = new JFrame("Pause The Game");
        askFrame = new JFrame("Leave?");

        font = new Font("new",Font.PLAIN,18);

        bgColor = new Color(102,102,102);
        fgColor = new Color(0,102,0);

        
    }

    public void start(){
        createPauseMenu();
    }

    public void createPauseMenu(){
        pauseFrame.setLayout(null);
        pauseFrame.setLocation(400,220);
        pauseFrame.setSize(505,370);
        pauseFrame.setResizable(false);
        pauseFrame.setContentPane(pauseBg);

        pauseBg.setLayout(null);

        //resume button
        resumeButton.setBounds(75,258,160,32);
        makeButton(resumeButton);


        //save game buttons
        saveButton.setBounds(255,258,170,32);
        makeButton(saveButton);

        exitButton.setBounds(150,292,180,32);
        makeButton(exitButton);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                askUser();
            }
        });

        pauseBg.add(resumeButton);
        pauseBg.add(saveButton);
        pauseBg.add(exitButton);


        pauseFrame.setVisible(true);


    }

    //This method sets features of the given button
    public void makeButton(JButton button){
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
    }

    //This method asks users if they are sure they want to leave
    public void askUser(){
        askFrame.setLayout(null);
        askFrame.setLocation(450,250);
        askFrame.setSize(400,300);
        askFrame.setResizable(false);
        askFrame.setContentPane(askBg);

        askBg.setLayout(null);

        //leave button
        leaveButton.setBounds(32,222,152,42);
        makeButton(leaveButton);

        //cancel button
        cancelButton.setBounds(204,222,152,42);
        makeButton(cancelButton);

        askBg.add(leaveButton);
        askBg.add(cancelButton);


        askFrame.setVisible(true);

    }
}

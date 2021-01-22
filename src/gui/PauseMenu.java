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
    private final JButton resumeButton;
    private final JButton saveButton;
    private final JButton exitButton;
    private final JButton cancelButton;
    private final JButton leaveButton;
    //background pics
    private Background pauseBg;
    private Background askBg;
    private ImageIcon img;
    //background label
    private final JLabel pauseBgLabel;
    //frames
    private final JFrame pauseFrame;
    private final JFrame askFrame;

    //colors
    private final Color bgColor;//background color
    private final Color fgColor;//foreground color


    private final Font font;

    {
        try {
            pauseBg = new Background(".\\Extras\\pause_menu.jpeg");
            askBg = new Background(".\\Extras\\ask_user1.jpeg");
            img = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower.gif");
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

        pauseBgLabel = new JLabel();

        
    }

    public void start(){
        createPauseMenu();
    }

    public void createPauseMenu(){
        //extra label and panel
        JPanel pauseBgPanel = new JPanel();
        JLabel msg = new JLabel("Your Game Is Paused!",SwingConstants.CENTER);
        msg.setFont(new Font("new",Font.PLAIN,24));
        msg.setBackground(new Color(51,51,51));
        msg.setForeground(new Color(255,255,153));
        msg.setOpaque(true);
        pauseBgPanel.setLayout(new BorderLayout());
        //main frame for pausing
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

        //add image to label
        pauseBgLabel.setIcon(img);
        pauseBgLabel.setBackground(new Color(51,51,51));
        pauseBgPanel.setBounds(20,80,460,170);
        pauseBgLabel.setOpaque(true);
        pauseBgPanel.add(pauseBgLabel,BorderLayout.WEST);
        pauseBgPanel.add(msg,BorderLayout.CENTER);

        //exit to main menu button
        exitButton.setBounds(150,292,180,32);
        makeButton(exitButton);
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                askUser();
            }
        });

        pauseBg.add(pauseBgPanel);
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

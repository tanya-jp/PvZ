package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class creates UI for pause menu and adds needed buttons.
 * Each time the game is paused, this UI appears.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */
public class PauseMenu {
    //buttons
    //resume button t go back to game
    private final JButton resumeButton;

    //save the game button
    private final JButton saveButton;

    //exit to main menu button which opens another menu to ask user
    private final JButton exitButton;

    //cancel exiting button
    private final JButton cancelButton;

    //go back to main menu button
    private final JButton leaveButton;


    //background pics
    //pause background
    private Background pauseBg;

    //ask user background
    private Background askBg;

    //image icon to keep sunflower gif
    private ImageIcon img;

    
    //background label
    private final JLabel pauseBgLabel;

    //frames
    //pause frame
    private final JFrame pauseFrame;
    //ask user frame
    private final JFrame askFrame;

    //colors
    private final Color bgColor;//background color
    private final Color fgColor;//foreground color

    //number of times resume button is clicked
    private boolean resumeClicked;

    //number of times exit button is clicked
    private boolean exitClicked;

    //number of times leave button is clicked
    private boolean leaveClicked;

    //number of times leave button is clicked
    private boolean saveClicked;

    //font for buttons
    private final Font font;

    //create all images and handling exceptions
    {
        try {
            pauseBg = new Background(".\\Extras\\pause_menu.jpeg");
            askBg = new Background(".\\Extras\\ask_user1.jpeg");
            img = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\sun_flower.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor to create all needed objects.
     */
    public PauseMenu(){
        //create buttons
        //create back to game button
        resumeButton = new JButton("Back To Game");

        //create save button
        saveButton = new JButton("Save Your Game");

        //create exit button
        exitButton = new JButton("Go To Main Menu");

        //create cancel button
        cancelButton = new JButton("Cancel");

        //create leave button (appears on ask frame)
        leaveButton = new JButton("Leave");

        //frames
        //create game pause menu
        pauseFrame = new JFrame("Pause The Game");

        //create frame to ask if player wants to leave
        askFrame = new JFrame("Leave?");

        //create new font
        font = new Font("new",Font.PLAIN,18);

        //create background color
        bgColor = new Color(102,102,102);

        //create foreground color
        fgColor = new Color(0,102,0);

        //create pause background label
        pauseBgLabel = new JLabel();

        //to check if resume button is clicked
        resumeClicked = false;

        //to check if exit button is clicked
        exitClicked = false;

        //to check if leave button is clicked
        leaveClicked = false;

        //to check if save button is clicked
        saveClicked = false;
    }

    /**
     * This method calls create pause menu.
     */
    public void start(){
        createPauseMenu();
    }

    /**
     * This method creates pause menu an adds needed components to it.
     * It also adds needed action listeners.
     */
    public void createPauseMenu(){
        //extra panel to add pause massage to
        JPanel pauseBgPanel = new JPanel();

        //label to show pause massage
        JLabel msg = new JLabel("Your Game Is Paused!",SwingConstants.CENTER);

        //set massage font
        msg.setFont(new Font("new",Font.PLAIN,24));
        //set massage colors
        msg.setBackground(new Color(51,51,51));
        msg.setForeground(new Color(255,255,153));
        msg.setOpaque(true);

        //set panel layout to borderLayout
        pauseBgPanel.setLayout(new BorderLayout());


        //set features for pause frame
        pauseFrame.setLayout(null);
        pauseFrame.setLocation(400,220);
        pauseFrame.setSize(505,370);
        pauseFrame.setResizable(false);
        //set pause frame content to pause background
        pauseFrame.setContentPane(pauseBg);

        //set pause background layout to null
        pauseBg.setLayout(null);

        //manually set bounds for resume button
        resumeButton.setBounds(75,258,160,32);

        //set button features using makeButton method
        makeButton(resumeButton);

        //add mouse listener to resume button to close pause frame
        // and set click status to false

        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resumeClicked = true;
                pauseFrame.setVisible(false);
            }
        });


        //manually set bounds for save button
        saveButton.setBounds(255, 258, 170, 32);

        //set save button features using makeButton method
        makeButton(saveButton);


        //add mouse listener to change click status
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveClicked = true;
            }
        });

        //add image to pause label
        pauseBgLabel.setIcon(img);

        //set pause label features
        pauseBgLabel.setBackground(new Color(51,51,51));
        pauseBgPanel.setBounds(20,80,460,170);
        pauseBgLabel.setOpaque(true);

        //add label and massage to pause panel
        pauseBgPanel.add(pauseBgLabel,BorderLayout.WEST);
        pauseBgPanel.add(msg,BorderLayout.CENTER);

        //manually set bounds for exit button
        exitButton.setBounds(150,292,180,32);
        //set exit button features
        makeButton(exitButton);

        //add mouse listener to check click status
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitClicked = true;
            }
        });

        //add all components to background
        pauseBg.add(pauseBgPanel);
        pauseBg.add(resumeButton);
        pauseBg.add(saveButton);
        pauseBg.add(exitButton);


        //show pause frame
        pauseFrame.setVisible(true);

    }

    /**
     * This method gets a button and adds color and font to it.
     * @param button given button
     */
    public void makeButton(JButton button){
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(font);
    }


    /**
     * This method handles the frame for asking the player.
     * It adds leave button and cancel button to the frame
     * with their mouse listeners.
     */
    public void askUser(){
        //set frame layout to null
        askFrame.setLayout(null);
        //set features
        askFrame.setLocation(450,250);
        askFrame.setSize(400,300);
        askFrame.setResizable(false);
        //set content to ask background
        askFrame.setContentPane(askBg);

        //set ask background layout to null
        askBg.setLayout(null);

        //manually set bounds for leave button
        leaveButton.setBounds(32,222,152,42);
        //set features
        makeButton(leaveButton);

        //manually set bounds for cancel button
        cancelButton.setBounds(204,222,152,42);
        //set features
        makeButton(cancelButton);

        //add buttons to background
        askBg.add(leaveButton);
        askBg.add(cancelButton);

        //add mouse listener to check click status
        //also closes ask frame
        leaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                askFrame.setVisible(false);
                pauseFrame.setVisible(false);
                leaveClicked = true;
            }
        });

        //add mouse listener to check click status
        //also closes ask frame
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                exitClicked = false;
                askFrame.setVisible(false);
            }
        });


        //show ask frame
        askFrame.setVisible(true);

    }

    /**
     * gets exit button status.
     * @return exitClicked field
     */
    public boolean isExitClicked() {
        return exitClicked;
    }

    /**
     * This method changes resume button status to false.
     */
    public void falseResumeButton(){resumeClicked = false;}

    /**
     * gets leave button status.
     * @return leaveClicked field
     */
    public boolean isLeaveClicked() {
        return leaveClicked;
    }

    /**
     * gets resume button status.
     * @return resumeClicked field
     */
    public boolean isResumeClicked() {
        return resumeClicked;
    }

    /**
     * gets pause frame object.
     * @return pause frame field
     */
    public JFrame getPauseFrame() {
        return pauseFrame;
    }

    /**
     * gets ask frame object.
     * @return ask frame field
     */
    public JFrame getAskFrame() {
        return askFrame;
    }

    /**
     * This method changes save button status to false.
     */
    public void falseSaveButton(){saveClicked = false;}

    /**
     * gets save button status.
     * @return saveClicked field
     */
    public boolean isSaveClicked() {
        return saveClicked;
    }
}

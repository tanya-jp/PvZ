package gui;

import javax.swing.*;

public class PauseMenu {
    //buttons
    private JButton resumeButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton cancelButton;
    private JButton leaveButton;

    public PauseMenu(){
        resumeButton = new JButton("Resume Game");
        saveButton = new JButton("Save Your Game");
        exitButton = new JButton("Go To Main Menu");//will also save the game
        cancelButton = new JButton("Cancel");
        leaveButton = new JButton("Leave");
    }

    public void createPauseMenu(){

    }

    public void askUser(){

    }
}

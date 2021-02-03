package network;


import gui.User;
import manager.StartManager;
import utils.FileUtils;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

/**
 * This class handles each player.
 * We execute this class whenever a new player wants to join.
 * All other classes are accessed via StartManager.
 * @version 1.0 2021
 * @author Elaheh Akbari and Tanya Djavaherpour
 */
public class Player {
    //path to users folder
    private static final String PATH = ".\\users\\";

    //player name
    private static String name;
    public static void main(String[] args) {
        //Set nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        //create start page UI
        StartManager.getMainMenu().createStartGUI();

        //set music to on by default
        StartManager.setMusic("on");

        //call update method
        StartManager.update();

        //call select method
        StartManager.select();

        //needed fields

        //login button
        JButton logButton;

        //sign up button
        JButton signButton;

        //change username button
        JButton changeUserButton;

        //show scoreboard button
        JButton scoreboardButton;

        //show unfinished games button
        JButton loadButton;
        //user
        User user = StartManager.getMainMenu().getUser();

        //login button
        logButton = StartManager.getMainMenu().getUser().getLoginButton();

        //sign up button
        signButton = StartManager.getMainMenu().getUser().getSignUpButton();

        //change username button
        changeUserButton = StartManager.getMainMenu().getUser().getRenameButton();

        //scoreboard button
        scoreboardButton = StartManager.getMainMenu().getRankingButton();


        //show games to load button
        loadButton = StartManager.getMainMenu().getLoadButton();

        //get server from StartManager
        Server server = StartManager.getServer();

        //add mouse listener to sign up button
        //information is checked via server
        signButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //set username field when inserted
                user.getNewUserField().setText(user.getNewUserField().getText());

                //user name field
                String newUsername = user.getNewUserField().getText().toLowerCase();

                //check if username field is empty
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }

                //check if sign up is available
                else if (server.isSignUpAvailable(newUsername)) {
                    //call login method
                    StartManager.getMainMenu().login();

                    //set username label
                    StartManager.getMainMenu().getUsernameLabel().setText(newUsername);

                    //make a new folder for this player
                    FileUtils.makeFolder(PATH+newUsername);
                    FileUtils.fileWriter("score\n0",PATH+newUsername+"\\");
                    name = user.getNewUserField().getText().toLowerCase();
                } //show proper massage if username isn't available
                else
                    JOptionPane.showMessageDialog(signButton, "Username Is" +
                            " Already Taken.");
            }
        });

        //add mouse listener to login button
        //information is checked via server
        logButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //set username field when inserted
                user.getNewUserField().setText(user.getNewUserField().getText());

                //user field
                String newUsername = user.getNewUserField().getText().toLowerCase();

                //check if username field is empty
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }
                else if (server.isLoginAvailable(newUsername)) {
                    //call login method
                    StartManager.getMainMenu().login();

                    //set username label
                    StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
                } //show proper massage if username isn't available
                else
                    JOptionPane.showMessageDialog(signButton, "Incorrect Username!");
            }
        });

        //add mouse listener to change username button
        //information is checked via server
        changeUserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //get old username
                String oldUsername = StartManager.getMainMenu().getUsernameLabel().getText().toLowerCase();

                //get new username
                String newUsername = StartManager.getMainMenu().getUser().getRenameUserField().getText().toLowerCase();

                //check if username field is empty
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }
                else if (server.isChangeAvailable(oldUsername,newUsername)) {
                    //show proper massage if change has been made
                    JOptionPane.showMessageDialog(changeUserButton,"Changed" +
                            "Successfully!");

                    //close the frame
                    StartManager.getMainMenu().getUser().getRenameFrame().setVisible(false);

                    //set username label
                    StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
                } //show proper massage if username isn't available
                else {
                    JOptionPane.showMessageDialog(signButton, "Not Available :(");
                }
            }
        });

        //add mouse listener to load games button
        //information is checked via server
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //show the board
                StartManager.getMainMenu().showLoadBoard();

                //create the frame
                StartManager.getMainMenu().getLoadBoard().createLoadFrame();

                //add mouse listener to load button
                StartManager.getMainMenu().getLoadBoard().getLoadButton().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //get selected game
                        StartManager.getMainMenu().setGameNumber(
                                StartManager.getMainMenu().getLoadBoard().selectedItems());

                        //close main menu frame
                        StartManager.getMainMenu().disable();

                        //close load board
                        StartManager.getMainMenu().getLoadBoard().disable();

                        //reload the game
                        StartManager.startGame();
                    }
                });
            }
        });


        //add mouse listener to scoreboard button
        //information is checked via server
        scoreboardButton.addMouseListener(new MouseAdapter() {
            int numOfClicks = 0;
            @Override
            public void mouseClicked(MouseEvent e) {
                numOfClicks++;
                //create a board with given information from server
                StartManager.getMainMenu().getScoreboard().createBoard(server.returnAllInfo());
            }
        });

        //create client socket
        //handle possible exception
        try (Socket client = new Socket("localhost", 3040);) {
            System.out.println("Client connected.");

            //exit the game if quit button is pressed
            StartManager.getMainMenu().getQuitButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Client closed.");
                    System.exit(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get username.
     * @return name field
     */
    public static String getName() {
        return name;
    }
}

package network;

import gui.MainMenu;
import gui.User;
import manager.StartManager;
import utils.FileUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Player {
    private static final String PATH = ".\\users\\";
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
        //StartManager startManager = new StartManager();
        StartManager.getMainMenu().createStartGUI();
        StartManager.setMusic("on");
        StartManager.update();

        //needed fields
//        final String newUsername = "";
        JButton logButton;
        JButton signButton;
        JButton changeUserButton;
        JButton scoreboardButton;
        JTextField renameUserField;
        String mode; // hard or normal
        String type; //day or night
        int numOfWins = 2;
        int numOfLooses = 0;
        int score = 100;

        //user
        User user = StartManager.getMainMenu().getUser();
//        user.getNewUserField().setText(user.getNewUserField().getText());
//        //user field
//        newUsername = user.getNewUserField().getText().toLowerCase();
        //login button
        logButton = StartManager.getMainMenu().getUser().getLoginButton();
        //sign up button
        signButton = StartManager.getMainMenu().getUser().getSignUpButton();
        //change username button
        changeUserButton = StartManager.getMainMenu().getUser().getRenameButton();
        //scoreboard button
        scoreboardButton = StartManager.getMainMenu().getRankingButton();
        //rename user field
        renameUserField = StartManager.getMainMenu().getUser().getRenameUserField();
        //mode
        mode = StartManager.getMainMenu().getSettings().getModeButton().getText().toLowerCase();
        //type
        type = StartManager.getMainMenu().getSettings().getTypeButton().getText().toLowerCase();
        Server server = StartManager.getServer();

        signButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.getNewUserField().setText(user.getNewUserField().getText());
                //user field
                String newUsername = user.getNewUserField().getText().toLowerCase();
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }
                else if (server.isSignUpAvailable(newUsername)) {
                        StartManager.getMainMenu().login();
                        StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
                        //make a new folder for this player
                        FileUtils.makeFolder(PATH+newUsername);
                        FileUtils.fileWriter("score\n0",PATH+newUsername+"\\");
                        name = user.getNewUserField().getText().toLowerCase();
//                        StartManager.setUserName(name);
                    } else
                        JOptionPane.showMessageDialog(signButton, "Username Is" +
                                " Already Taken.");
            }
        });


        logButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.getNewUserField().setText(user.getNewUserField().getText());
                //user field
                String newUsername = user.getNewUserField().getText().toLowerCase();
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }
                else if (server.isLoginAvailable(newUsername)) {
                    StartManager.getMainMenu().login();
                    StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
                } else
                    JOptionPane.showMessageDialog(signButton, "Incorrect Username!");
            }
        });


        changeUserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String oldUsername = StartManager.getMainMenu().getUsernameLabel().getText().toLowerCase();
                String newUsername = StartManager.getMainMenu().getUser().getRenameUserField().getText().toLowerCase();
                if(newUsername.equals("")) {
                    JOptionPane.showMessageDialog(signButton, "Please Fill " +
                            "The Field");
                }
                else if (server.isChangeAvailable(oldUsername,newUsername)) {
                    JOptionPane.showMessageDialog(changeUserButton,"Changed" +
                            "Successfully!");
                    StartManager.getMainMenu().getUser().getRenameFrame().setVisible(false);
                    StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
                } else {
                    JOptionPane.showMessageDialog(signButton, "Not Available :(");
                }
            }
        });


        scoreboardButton.addMouseListener(new MouseAdapter() {
            int numOfClicks = 0;
            @Override
            public void mouseClicked(MouseEvent e) {
                numOfClicks++;
                if(numOfClicks == 1) {
                    StartManager.getMainMenu().getScoreboard().createBoard();
                    for (int i = 0; i < server.getNumOfUsers(); i++) {
  //                      StartManager.getMainMenu().getScoreboard().createBoard();
                        StartManager.getMainMenu().getScoreboard().updateNormalBoard(server.returnInfo());
//                        StartManager.getMainMenu().getScoreboard().updateHardBoard(server.returnUserAndInfoHard());
                    }
                }
                else if (numOfClicks > 1)
                    StartManager.getMainMenu().getScoreboard().getRankFrame().setVisible(true);
            }
        });

        try (Socket client = new Socket("localhost", 2011);) {
            System.out.println("Client connected.");

            DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter request: ");
                String clientRequest = scanner.nextLine() + "\n";
                out.writeUTF(clientRequest);
                out.flush();
                System.out.println("Request sent.");
                if (clientRequest.equals("over\n")) break;
                System.out.print("Server response: " + "\n" + in.readUTF() + "\n");
            }
            System.out.println("All messages sent.");
            scanner.close();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Client closed.");
    }

    public static String getName() {
        return name;
    }
}
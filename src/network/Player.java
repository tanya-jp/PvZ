package network;

import gui.MainMenu;
import gui.User;
import manager.StartManager;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Player {

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
                            "The field");
                }
                else if (server.isSignUpAvailable(newUsername)) {
                        StartManager.getMainMenu().login();
                        StartManager.getMainMenu().getUsernameLabel().setText(newUsername);
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
                            "The field");
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
                super.mouseClicked(e);
            }
        });

        try (Socket client = new Socket("localhost", 5000);) {
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

 //   public void
}
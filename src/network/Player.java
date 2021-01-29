//package network;
//
//import gui.MainMenu;
//import manager.StartManager;
//
//import javax.swing.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.*;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Player {
//
//    public static void main(String[] args) {
//        //Set nimbus look and feel
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            System.err.println(e.getMessage());
//        }
//        //StartManager startManager = new StartManager();
//        StartManager.getMainMenu().createStartGUI();
//        StartManager.update();
//        String newUsername;
//        JButton okButton;
//        newUsername = StartManager.getMainMenu().getUser().getNewUserField().getText().toLowerCase();
//        okButton = StartManager.getMainMenu().getUser().getCreateButton();
//
//        okButton.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                StartManager.getMainMenu().login();
////                if(newUsername.equals("player"))
////                    StartManager.getMainMenu().login();
////                else
////                    System.out.println(newUsername);
//            }
//        });
//
//        try (Socket client = new Socket("localhost", 6023);) {
//            System.out.println("Client connected.");
//
//            DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
//            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
//
//            Scanner scanner = new Scanner(System.in);
//            while (true) {
//                System.out.print("Enter request: ");
//                String clientRequest = scanner.nextLine() + "\n";
//                out.writeUTF(clientRequest);
//                out.flush();
//                System.out.println("Request sent.");
//                if (clientRequest.equals("over\n")) break;
//                System.out.print("Server response: " + "\n" + in.readUTF() + "\n");
//            }
//            System.out.println("All messages sent.");
//            scanner.close();
//            in.close();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Client closed.");
//    }
//
// //   public void
//}
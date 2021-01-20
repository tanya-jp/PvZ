package com.company;

import gui.MainMenu;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        //Set nimbus look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        MainMenu mainMenu = new MainMenu();
    }
}
package com.company;

import manager.StartManager;

import javax.swing.*;

/**
 * Main class to execute the program and set look and feel.
 * an object of StartManager is created.
 * @author Tanya Djavaherpour and Elaheh Akbari
 * @version 1.0 2021
 */
public class Main {

    public static void main(String[] args){
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

        //create start manager
        StartManager startManager = new StartManager();
    }
}
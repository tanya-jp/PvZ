package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoadBoard {
    //background image
    private Background loadBg;
    //main frame
    private JFrame loadFrame;
    //main panel
    private JPanel loadPanel;
    //button
    private JButton loadButton;

    {
        try {
            loadBg = new Background(".\\Extras\\loadgame_bg.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoadBoard(){
        loadFrame = new JFrame("Load A Game");
        loadPanel = new JPanel();
        loadButton = new JButton("Load");
    }

    public void createLoadFrame(){
        //create start frame
        loadFrame.setSize(800,450);
        loadFrame.setLocationRelativeTo(null);
        loadFrame.setResizable(false);
//        loadFrame.setLayout(new GridLayout(2,1,5,5));
        loadFrame.setContentPane(loadBg);

        loadBg.setLayout(null);

        loadPanel.setBounds(70,70,300,300);
        loadPanel.setLayout(new GridLayout(5,1,5,5));

        JRadioButton game1 = new JRadioButton("1");
        JRadioButton game2 = new JRadioButton("2");
        JRadioButton game3 = new JRadioButton("3");
        JRadioButton game4 = new JRadioButton("4");

        loadPanel.add(game1);
        loadPanel.add(game2);
        loadPanel.add(game3);
        loadPanel.add(game4);
        loadPanel.add(loadButton);

        loadBg.add(loadPanel);
        loadFrame.setVisible(true);

    }

    public void updateBoard(String gameNum){
        
    }

}

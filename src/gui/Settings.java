package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.Year;

public class Settings {

    //buttons
    private JButton modeButton;
    private JButton typeButton;
    private JButton soundButton;
    private JButton choosePlantsButton;
    private JButton okButton;

    private ImageIcon onIcon;
    private ImageIcon offIcon;

    private Color bgColor = new Color(255,255,153);

    private Background settingsBg;

    private JFrame settingsFrame;

    private JPanel settingsPanel;

    {
        try {
            settingsBg = new Background(".\\Extras\\settings_bg.jpeg");
            onIcon = new ImageIcon(".\\Extras\\on_icon.png");
            offIcon = new ImageIcon(".\\Extras\\off_icon.png");
         //   mainBackground = new Background(".\\Extras\\main_bg.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Settings(){
        //create buttons
        modeButton = new JButton("Normal");
        typeButton = new JButton("Day");
        soundButton = new JButton("On");
        choosePlantsButton = new JButton("Plants");
        okButton = new JButton("Done");

        //create frames and panels
        settingsFrame = new JFrame("Settings");
        settingsPanel = new JPanel();

    }

    public void createSettings(){
        //set settings frame features
        settingsFrame.setLayout(null);
        settingsFrame.setLocation(450,250);
        settingsFrame.setSize(500,400);
        settingsFrame.setResizable(false);
        settingsFrame.setContentPane(settingsBg);

        settingsBg.setLayout(null);

        settingsPanel.setSize(453,285);
        settingsPanel.setBackground(new Color(102,0,153));
        settingsPanel.setLocation(20,68);
        settingsPanel.setLayout(null);

        modeButton.setBounds(10,10,100,40);
        modeButton.setBackground(bgColor);
        modeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(modeButton.getBackground().equals(bgColor)){
                    modeButton.setBackground(Color.DARK_GRAY);
                    modeButton.setText("Hard");
                }
                else {
                    modeButton.setBackground(bgColor);
                    modeButton.setText("Normal");
                }
            }
        });

        //button to set type of the game
        typeButton.setBounds(110,10,100,40);
        typeButton.setBackground(Color.YELLOW);
        typeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(typeButton.getBackground().equals(Color.YELLOW)){
                    typeButton.setBackground(Color.CYAN);
                    typeButton.setText("Night");
                }
                else {
                    typeButton.setBackground(Color.YELLOW);
                    typeButton.setText("Day");
                }
            }
        });

        //sound button features
        soundButton.setBounds(10,50,100,40);
        soundButton.setBackground(bgColor);
        soundButton.setIcon(onIcon);
        soundButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(soundButton.getBackground().equals(bgColor)){
                    soundButton.setBackground(Color.darkGray);
                    soundButton.setIcon(offIcon);
                    soundButton.setText("Off");
                }
                else {
                    soundButton.setBackground(bgColor);
                    soundButton.setIcon(onIcon);
                    soundButton.setText("On");
                }
            }
        });

        choosePlantsButton.setBounds(110,50,100,40);
        okButton.setBounds(10,90,200,40);

        settingsPanel.add(modeButton);
        settingsPanel.add(typeButton);
        settingsPanel.add(soundButton);
        settingsPanel.add(choosePlantsButton);
        settingsPanel.add(okButton);

        settingsBg.add(settingsPanel);

        settingsFrame.setVisible(true);
    }
}

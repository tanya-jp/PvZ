package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Settings {

    //buttons
    private final JButton modeButton;
    private final JButton typeButton;
    private final JButton soundButton;
    private final JButton choosePlantsButton;
    private final JButton okButton;

    private ImageIcon onIcon;
    private ImageIcon offIcon;
    private Background plantsBg;

    private final Color bgColor = new Color(255,255,153);

    private Background settingsBg;

    private final JFrame settingsFrame;
    private JFrame plantsFrame;

    //cards
    private ImageIcon cherry;
    private Image freezePee;
    private Image peeShooter;
    private Image sunflower;
    private Image wallNut;
    private Image squash;
    private Image mushroom;

    public JButton getTypeButton() {
        return typeButton;
    }

    public JButton getModeButton() {
        return modeButton;
    }

    public JFrame getSettingsFrame() {
        return settingsFrame;
    }


    private final JPanel settingsPanel;

    {
        try {
            settingsBg = new Background(".\\Extras\\setting_bg.jpeg");
            onIcon = new ImageIcon(".\\Extras\\on_icon.png");
            offIcon = new ImageIcon(".\\Extras\\off_icon.png");
            plantsBg = new Background(".\\Extras\\choosePlants_bg.jpeg");
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
        plantsFrame = new JFrame();

        //create images
        cherry = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png");
        freezePee = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_freezepeashooter.png").getImage();
        peeShooter = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_peashooter.png").getImage();
        sunflower = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_sunflower.png").getImage();
        wallNut = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_wallnut.png").getImage();
        //    squash = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png").getImage();
        //    mushroom = new ImageIcon(".\\PVS Design Kit\\images\\Cards\\card_cherrybomb.png").getImage();

    }

//    public void start(){
//        modeButton.setBackground(bgColor);
//
//    }

    public void createSettings(){
        //set settings frame features
        settingsFrame.setLayout(null);
        settingsFrame.setLocation(450,260);
        settingsFrame.setSize(350,290);
        settingsFrame.setResizable(false);
        settingsFrame.setContentPane(settingsBg);

        settingsBg.setLayout(null);

        settingsPanel.setSize(295,150);
        settingsPanel.setBackground(new Color(102,0,153));
        settingsPanel.setLocation(30,65);
        settingsPanel.setLayout(null);

        modeButton.setBounds(40,10,100,40);
        modeButton.setBackground(bgColor);
        modeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(modeButton.getBackground().equals(bgColor)){
                    modeButton.setBackground(Color.DARK_GRAY);
                    modeButton.setText("Hard");
                    System.out.println("changed to hard");
                }
                else {
                    modeButton.setBackground(bgColor);
                    modeButton.setText("Normal");
                    System.out.println("changed to normal");
                }
            }
        });

        //button to set type of the game
        typeButton.setBounds(140,10,100,40);
        typeButton.setBackground(Color.YELLOW);
        typeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(typeButton.getBackground().equals(Color.YELLOW)){
                    typeButton.setBackground(Color.CYAN);
                    typeButton.setText("Night");
//                    System.out.println("to night");
                }
                else {
                    typeButton.setBackground(Color.YELLOW);
                    typeButton.setText("Day");
//                    System.out.println("to day");
                }
            }
        });

        //sound button features
        soundButton.setBounds(40,50,100,40);
        soundButton.setBackground(bgColor);
        soundButton.setIcon(onIcon);
        soundButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(soundButton.getBackground().equals(bgColor)){
                    soundButton.setBackground(Color.darkGray);
                    soundButton.setIcon(offIcon);
                    soundButton.setText("Off");
//                    System.out.println("to off");
                }
                else {
                    soundButton.setBackground(bgColor);
                    soundButton.setIcon(onIcon);
                    soundButton.setText("On");
//                    System.out.println("to on");
                }
            }
        });

        choosePlantsButton.setBounds(140,50,100,40);
        choosePlantsButton.setBackground(new Color(0,255,51));
        choosePlantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                choosePlants();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                choosePlantsButton.setBackground(Color.GREEN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                choosePlantsButton.setBackground(new Color(0,255,51));
            }
        });

        okButton.setBounds(40,90,200,40);
//        okButton.setBackground(Color.GREEN);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                soundButton.setText(soundButton.getText());
                settingsFrame.setVisible(false);
                //also we need to save all
                //the changes that were made
                //in this action listener
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                okButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setBackground(Color.lightGray);
            }
        });

        settingsPanel.add(modeButton);
        settingsPanel.add(typeButton);
        settingsPanel.add(soundButton);
        settingsPanel.add(choosePlantsButton);
        settingsPanel.add(okButton);

        settingsBg.add(settingsPanel);

        settingsFrame.setVisible(true);
    }

    private void choosePlants(){
        plantsFrame.setLayout(null);
        plantsFrame.setLocation(450,250);
        plantsFrame.setSize(700,400);
        plantsFrame.setResizable(false);
        plantsFrame.setContentPane(plantsBg);

        plantsBg.setLayout(null);

        JPanel plantsPanel = new JPanel(null);
        plantsPanel.setBackground(new Color(102,0,153));
        plantsPanel.setBounds(30,50,450,290);
        JLabel cherryLabel = new JLabel(cherry);
        cherryLabel.setBounds(10,10,80,90);
        cherryLabel.setBackground(Color.GRAY);
        cherryLabel.setOpaque(true);

        plantsPanel.add(cherryLabel);
//        plantsPanel.add(new JLabel());
//        plantsPanel.add(new JLabel());
//        plantsPanel.add(new JLabel());
//        plantsPanel.add(new JLabel());
//        plantsPanel.add(new JLabel());

        plantsBg.add(plantsPanel);

        plantsFrame.setVisible(true);

    }

    public JButton getOkButton() {
        return okButton;
    }
}

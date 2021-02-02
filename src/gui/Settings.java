package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class keeps all settings components and creates the frame.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */
public class Settings {

    //buttons
    //normal/hard button
    private final JButton modeButton;

    //day/ night button
    private final JButton typeButton;

    //mute/unmute button
    private final JButton soundButton;

    //show plants button
    private final JButton choosePlantsButton;

    //done button
    private final JButton okButton;

    //sound on icon
    private ImageIcon onIcon;

    //sound off icon
    private ImageIcon offIcon;

    //plants frame background
    private Background plantsBg;

    //new color
    private final Color bgColor = new Color(255,255,153);

    //settings background
    private Background settingsBg;

    //frame for settings
    private final JFrame settingsFrame;

    //frame for showing plants
    private JFrame plantsFrame;

    //images for all cards
    private ImageIcon cherry;
    private Image freezePee;
    private Image peeShooter;
    private Image sunflower;
    private Image wallNut;
    private Image squash;
    private Image mushroom;

    /**
     * gets day/ night button
     * @return type button
     */
    public JButton getTypeButton() {
        return typeButton;
    }

    /**
     * get normal/hard button
     * @return mode button
     */
    public JButton getModeButton() {
        return modeButton;
    }


    /**
     * gets settings frame
     * @return settings frame object
     */
    public JFrame getSettingsFrame() {
        return settingsFrame;
    }

    /**
     * gets done button
     * @return ok button field
     */
    public JButton getOkButton() {
        return okButton;
    }

    /**
     * gets sounds button
     * @return sound button field
     */
    public JButton getSoundButton() {
        return soundButton;
    }


    //settings panel
    private final JPanel settingsPanel;


    //create needed backgrounds and handle possible exceptions
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

    /**
     * Constructor to create all needed fields
     */
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

    /**
     * This method creates main settings frame.
     * also adds all components to it.
     */
    public void createSettings(){
        //set settings frame features
        settingsFrame.setLayout(null);
        settingsFrame.setLocation(450,260);
        settingsFrame.setSize(350,290);
        settingsFrame.setResizable(false);

        //set content to settings background
        settingsFrame.setContentPane(settingsBg);

        //set layout to null
        settingsBg.setLayout(null);

        //set settings panel features
        settingsPanel.setSize(295,150);
        settingsPanel.setBackground(new Color(102,0,153));
        settingsPanel.setLocation(30,65);
        settingsPanel.setLayout(null);

        //manually set mode button bounds
        modeButton.setBounds(40,10,100,40);
        modeButton.setBackground(bgColor);

        //add mouse listener to change mode everytime the button is clicked
        //changes button text
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

        //manually set type button bounds
        typeButton.setBounds(140,10,100,40);
        typeButton.setBackground(Color.YELLOW);

        //add mouse listener
        //changes color with every click
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

        //manually set sound button bounds
        soundButton.setBounds(40,50,100,40);
        soundButton.setBackground(bgColor);
        //set on icon by default
        soundButton.setIcon(onIcon);

        //add mouse listener
        //text and icon change with every click
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

        //manually set show plants button bounds
        choosePlantsButton.setBounds(140,50,100,40);
        choosePlantsButton.setBackground(new Color(0,255,51));

        //add mouse listener
        //colors change with every enter or exit
        //choose plants method is called when clicked
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

        //manually set ok button bounds
        okButton.setBounds(40,90,200,40);

        //add mouse listener to ok button
        //colors change with every enter or exit
        //settings frame is closed when clicked
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingsFrame.setVisible(false);
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

        //add all
        settingsPanel.add(modeButton);
        settingsPanel.add(typeButton);
        settingsPanel.add(soundButton);
        settingsPanel.add(choosePlantsButton);
        settingsPanel.add(okButton);

        //add to background
        settingsBg.add(settingsPanel);

        //show frame
        settingsFrame.setVisible(true);
    }

    /**
     * This method opens a frame
     * in which all plants are shown.
     */
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

        plantsBg.add(plantsPanel);

        plantsFrame.setVisible(true);

    }
}

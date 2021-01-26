package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainMenu{
    private Background background;

    {
        try {
            background = new Background(".\\PVS Design Kit\\images\\first_screen.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ImageIcon startImg;

    private JButton newGameButton;
    private JButton loadButton;
    private JButton rankingButton;
    private JButton settingButton;
    private JButton quitButton;

    private JFrame startFrame;
    private JFrame mainFrame;

    private JPanel menuPanel;
    private JPanel bgPanel;
    private JPanel startPanel;

    public MainMenu(){
        //create buttons
        newGameButton = new JButton("New Game");
        loadButton = new JButton("Load Game");
        rankingButton = new JButton("Rankings");
        settingButton = new JButton("Setting");
        quitButton = new JButton("Quit");
        //create frames
        startFrame = new JFrame("Start");//start frame
        mainFrame = new JFrame("Plants Vs Zombies");//main frame
        //create panels
        menuPanel = new JPanel();
        bgPanel = new JPanel();

        createStartGUI();
    }
//This class creates the start GUI
    private void createStartGUI(){
        startPanel = new JPanel();
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(2,1,5,5));
        startFrame.setContentPane(background);

        startImg = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\click_to_start.gif");

        JLabel startLabel = new JLabel (startImg);
        startPanel.add(startLabel);

        background.setLayout(new BorderLayout());
        background.add(startLabel,BorderLayout.SOUTH);

        startFrame.pack();
        startFrame.setVisible(true);
//        startFrame.add(startPanel,BorderLayout.SOUTH);

    }

    private void createMainMenu(){

    }


}

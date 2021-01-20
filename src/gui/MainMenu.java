package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainMenu{
    private Background startBackground;
    private Background mainBackground;

    {
        try {
            startBackground = new Background(".\\PVS Design Kit\\images\\first_screen.jpg");
            mainBackground = new Background(".\\Extras\\main_bg.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ImageIcon startImg;

    private final JButton newGameButton;
    private final JButton loadButton;
    private final JButton rankingButton;
    private final JButton settingButton;
    private final JButton quitButton;

    private JFrame startFrame;
    private JFrame mainFrame;

    private JPanel menuPanel;
    private JPanel bgPanel;
    private JPanel startPanel;

    public MainMenu(){
        //create buttons
        newGameButton = new JButton("Adventure");
        loadButton = new JButton("Load Game");
        rankingButton = new JButton("Rankings");
        settingButton = new JButton("Setting");
        quitButton = new JButton("Quit Game");
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
//        startPanel = new JPanel();
        startFrame.setSize(1050,650);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(2,1,5,5));
        startFrame.setContentPane(startBackground);

        startImg = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\click_to_start.gif");

        JLabel startLabel = new JLabel (startImg);
//        startPanel.add(startLabel);

        startBackground.setLayout(new BorderLayout());
        startBackground.add(startLabel,BorderLayout.SOUTH);
        startBackground.revalidate();

        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createMainMenu();
            }
        });

//        startFrame.pack();
        startFrame.setVisible(true);
//        startFrame.add(startPanel,BorderLayout.SOUTH);

    }

    private void createMainMenu(){
        //set button shapes
        makeButtons();

//        menuPanel.setLayout(null);
        JPanel menuPanel = new JPanel();
        mainFrame.setLayout(null);
        mainFrame.setSize(1050,650);
        mainFrame.setLocationRelativeTo(null);
//        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainBackground);

        mainBackground.setLayout(null);


        menuPanel.setLayout(new GridLayout(5,1,5,5));
//        menuPanel.setLayout(null);
        menuPanel.add(newGameButton);
        menuPanel.add(loadButton);
        menuPanel.add(rankingButton);
        menuPanel.add(settingButton);
        menuPanel.add(quitButton);
        menuPanel.setBackground(Color.darkGray);

        menuPanel.setBounds(525,130,300,280);
        mainBackground.add(menuPanel);
        mainBackground.revalidate();

        mainFrame.setVisible(true);
    }

    public void makeButtons(){
        Font font = new Font("new",Font.ITALIC,20);

        newGameButton.setBackground(Color.darkGray);
        newGameButton.setFont(font);
        newGameButton.setForeground(Color.WHITE);
        changeColor(newGameButton);

        loadButton.setBackground(Color.darkGray);
        loadButton.setFont(font);
        loadButton.setForeground(Color.WHITE);
        changeColor(loadButton);

        rankingButton.setBackground(Color.darkGray);
        rankingButton.setFont(font);
        rankingButton.setForeground(Color.WHITE);
        changeColor(rankingButton);

        settingButton.setBackground(Color.darkGray);
        settingButton.setFont(font);
        settingButton.setForeground(Color.WHITE);
        changeColor(settingButton);

        quitButton.setBackground(Color.darkGray);
        quitButton.setFont(font);
        quitButton.setForeground(Color.WHITE);
        changeColor(quitButton);


    }

    public void changeColor(JButton button){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255,255,40));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

    }

}

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
    //menu buttons
    private final JButton newGameButton;
    private final JButton loadButton;
    private final JButton rankingButton;
    private final JButton settingButton;
    private final JButton quitButton;
    //extra button and label
    private JButton changeUsername;
    //label to show username of the player
    private JLabel usernameLabel;

    //start and main frame
    private final JFrame startFrame;
    private final JFrame mainFrame;

    private JPanel menuPanel;
    private JPanel bgPanel;
    private JPanel startPanel;

    public MainMenu(){
        //create buttons
        newGameButton = new JButton("Adventure");
        loadButton = new JButton("Load Game");
        rankingButton = new JButton("Leaderboards");
        settingButton = new JButton("Setting");
        quitButton = new JButton("Quit Game");
        changeUsername = new JButton("Change Profile");
        //create label
        usernameLabel = new JLabel("Username",SwingConstants.CENTER);//This changes with each user
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
        startFrame.setSize(1050,650);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(2,1,5,5));
        startFrame.setContentPane(startBackground);

        startImg = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\click_to_start.gif");

        JLabel startLabel = new JLabel (startImg);

        startBackground.setLayout(new BorderLayout());
        startBackground.add(startLabel,BorderLayout.SOUTH);
        startBackground.revalidate();

        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                createMainMenu();
                startFrame.setVisible(false);
                mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });

        startFrame.setVisible(true);

    }

    private void createMainMenu(){
        //set button shapes
        makeButtons();


        JPanel menuPanel = new JPanel();
        mainFrame.setLayout(null);
        mainFrame.setSize(1050,650);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainBackground);

        mainBackground.setLayout(null);


        menuPanel.setLayout(new GridLayout(4,1,5,5));
        //menu buttons
        newGameButton.setPreferredSize(new Dimension(300,80));
        loadButton.setPreferredSize(new Dimension(300,80));
        quitButton.setPreferredSize(new Dimension(300,80));
        //setting button
        settingButton.setBounds(15,530,100,55);
        //username label
        usernameLabel.setBounds(80,30,150,40);

        //change username button

//        newGameButton.setBounds(525,170,300,60);
//        loadButton.setBounds(525,230,300,60);
//        quitButton.setBounds(525,290,300,60);
        menuPanel.add(newGameButton);
        menuPanel.add(loadButton);
        menuPanel.add(rankingButton);
//        menuPanel.add(settingButton);
        menuPanel.add(quitButton);
        menuPanel.setBounds(525,130,290,320);
        menuPanel.setBackground(Color.darkGray);


        mainBackground.add(menuPanel);
        mainBackground.add(settingButton);
        mainBackground.add(usernameLabel);
        mainBackground.revalidate();

        mainFrame.setVisible(true);
    }

    public void makeButtons(){
        Font font = new Font("new",Font.ITALIC,20);

        newGameButton.setBackground(Color.darkGray);
        newGameButton.setFont(new Font("new",Font.ITALIC,32));
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

        //username label
        usernameLabel.setFont(font);
        usernameLabel.setBackground(new Color(51,0,0));
        usernameLabel.setOpaque(true);
        usernameLabel.setForeground(Color.WHITE);

//        changeUsername.setBackground(Color.darkGray);
//        changeUsername.setFont(new Font("change",Font.ITALIC,12));
//        changeUsername.setForeground(Color.WHITE);
//        changeColor(changeUsername);

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

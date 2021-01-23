package gui;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainMenu{
    PauseMenu pauseMenu = new PauseMenu();
    User user = new User();
    Scoreboard scoreboard = new Scoreboard();


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


    //menu buttons
    private final JButton newGameButton;
    private final JButton loadButton;
    private final JButton rankingButton;
    private final JButton settingButton;
    private final JButton quitButton;
    //extra button and label
    private final JLabel changeUsername;
    //label to show username of the player
    private final JLabel usernameLabel;

    //start and main frame
    private final JFrame startFrame;
    private final JFrame mainFrame;


    public MainMenu() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //create buttons
        newGameButton = new JButton("Adventure");
        loadButton = new JButton("Load Game");
        rankingButton = new JButton("Leaderboards");
        settingButton = new JButton("Setting");
        quitButton = new JButton("Quit Game");
        changeUsername = new JLabel("Change Profile",SwingConstants.CENTER);
        //create label
        usernameLabel = new JLabel("Username",SwingConstants.CENTER);//This changes with each user
        //create frames
        startFrame = new JFrame("Start");//start frame
        mainFrame = new JFrame("Plants Vs Zombies");//main frame

        createStartGUI();
    }

//This class creates the start GUI
    private void createStartGUI(){
        //create start frame
        startFrame.setSize(1050,650);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(2,1,5,5));
        startFrame.setContentPane(startBackground);

        ImageIcon startImg = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\click_to_start.gif");

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
        makeButtons(newGameButton);
        makeButtons(settingButton);
        makeButtons(loadButton);
        makeButtons(rankingButton);
        makeButtons(quitButton);
        //set labels
        makeLabels();


        JPanel menuPanel = new JPanel();
        mainFrame.setLayout(null);
        mainFrame.setSize(1050,625);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setContentPane(mainBackground);
        mainFrame.setResizable(false);

        mainBackground.setLayout(null);


        menuPanel.setLayout(new GridLayout(4,1,5,5));

        //setting button
        settingButton.setBounds(15,530,100,55);
        //username label
        usernameLabel.setBounds(80,30,150,40);

        //change username button
        changeUsername.setBounds(40,100,220,40);
        changeUsername.setVisible(false);

        menuPanel.add(newGameButton);
        menuPanel.add(loadButton);
        menuPanel.add(rankingButton);
        menuPanel.add(quitButton);
        menuPanel.setBounds(525,130,290,320);
        menuPanel.setBackground(Color.darkGray);


        mainBackground.add(menuPanel);
        mainBackground.add(settingButton);
        mainBackground.add(usernameLabel);
        mainBackground.add(changeUsername);
        mainBackground.revalidate();

        usernameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeUsername.setVisible(!changeUsername.isVisible());
//                try {
//                    audioPlayer();
//                } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ioException) {
//                    ioException.printStackTrace();
//                }

            }
        });

        changeUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.createUserFrame();
//                user.renameUserFrame();
            }
        });

        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        mainFrame.setVisible(true);


        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pauseMenu.start();
            }
        });

        rankingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scoreboard.createBoard();
            }
        });
    }

    public void makeLabels(){
        Font font = new Font("new",Font.ITALIC,20);
        //username label
        usernameLabel.setFont(font);
        usernameLabel.setBackground(new Color(51,0,0));
        usernameLabel.setOpaque(true);
        usernameLabel.setForeground(Color.WHITE);

        changeUsername.setBackground(new Color(51,0,0));
        changeUsername.setBorder(new LineBorder(new Color(255,255,100),5));
        changeUsername.setFont(new Font("change",Font.ITALIC,16));
        changeUsername.setForeground(Color.WHITE);
        changeUsername.setOpaque(true);
        changeUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeUsername.setForeground(new Color(255,255,40));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeUsername.setForeground(Color.WHITE);
            }
        });

    }

    public void makeButtons(JButton button){
        Font font = new Font("new",Font.ITALIC,20);

        newGameButton.setBackground(Color.darkGray);
        newGameButton.setFont(new Font("new",Font.ITALIC,32));
        newGameButton.setForeground(Color.WHITE);
        changeColor(newGameButton);

        button.setBackground(Color.darkGray);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        changeColor(button);

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

    public void audioPlayer() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Long currentFrame;
        Clip clip;

        // current status of clip
        String status;

        AudioInputStream audioInputStream;
        String filePath = ".\\PVS Design Kit\\sounds\\atebrains.wav";
            audioInputStream =
                    AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
    }

}

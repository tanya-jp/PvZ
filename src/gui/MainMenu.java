package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class handles start page and main menu page GUI
 * first the player is asked to enter a username and then the player
 * is directed to the main page, main menu is also managed here.
 * @author Elaheh Akbari and Tanya Djavaherpour
 * @version 1.0 2021
 */

public class MainMenu{
    //other classes that are needed

    //User UI
    User user = new User();

    //ScoreBoard UI
    Scoreboard scoreboard = new Scoreboard();

    //settings UI
    Settings settings = new Settings();

    //game loading board UI
    LoadBoard loadBoard;

    //to count the times of clicking settings button
    private int clicks = 0;

    //to detect which game is being played
    private String gameNumber = null;

    //back grounds for each page

    //first page background
    private Background startBackground;
    //main menu background
    private Background mainBackground;

    //create backgrounds and handle possible exception
    {
        try {
            startBackground = new Background(".\\PVS Design Kit\\images\\first_screen.jpg");
            mainBackground = new Background(".\\Extras\\main_bg.jpeg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //menu buttons
    //button to start a new game (Adventure)
    private final JButton newGameButton;

    //button to open unfinished game options
    private final JButton loadButton;

    //button to show ranking UI
    private final JButton rankingButton;

    //button to show settings
    private final JButton settingButton;

    //button to quit and end the game
    private final JButton quitButton;


    //label to open change username UI
    private final JLabel changeUsername;

    //label to show username of the player on main page
    private final JLabel usernameLabel;

    //first page (start) frame
    private final JFrame startFrame;

    //frame that includes main menu
    private final JFrame mainFrame;

    /**
     * constructor to create the fields
     */
    public MainMenu(){

        //create new game button
        newGameButton = new JButton("Adventure");
        //create load game button
        loadButton = new JButton("Load Game");
        //create ranking button
        rankingButton = new JButton("Leaderboards");
        //create setting button
        settingButton = new JButton("Setting");
        //create quit game button
        quitButton = new JButton("Quit Game");
        //create change username label
        changeUsername = new JLabel("Change Profile",SwingConstants.CENTER);
        //create username label
        usernameLabel = new JLabel("Username",SwingConstants.CENTER);//This changes with each user
        //create frames
        startFrame = new JFrame("Start");//start frame
        mainFrame = new JFrame("Plants Vs Zombies");//main frame
    }

    /**
     * This class creates the starting page UI
     * It adds mouseListener to start label and the user UI create button.
     */
    public void createStartGUI(){
        //set start frame features
        startFrame.setSize(1050,650);
        startFrame.setLocationRelativeTo(null);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //set layout
        startFrame.setLayout(new GridLayout(2,1,5,5));
        //set start frame content to background image
        startFrame.setContentPane(startBackground);

        //create a new image icon which is the start game gif
        ImageIcon startImg = new ImageIcon(".\\PVS Design Kit\\images\\Gifs\\click_to_start.gif");

        //creat a label to add the start gif to
        JLabel startLabel = new JLabel (startImg);

        //set layout
        startBackground.setLayout(new BorderLayout());
        //add start label to the bottom of background
        startBackground.add(startLabel,BorderLayout.SOUTH);
        startBackground.revalidate();

        //start label action listener
        startLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.createUserFrame();
            }
        });


        //show start frame
        startFrame.setVisible(true);
    }

    /**
     * this method creates the main page
     * It adds all needed buttons and labels to the background and then to the frame.
     */
    public void createMainMenu(){

        //set button shapes using make button method
        makeButtons(newGameButton);
        makeButtons(settingButton);
        makeButtons(loadButton);
        makeButtons(rankingButton);
        makeButtons(quitButton);

        //set labels features using make labels method
        makeLabels();

        //create a panel for menu buttons
        JPanel menuPanel = new JPanel();

        //set main frame features
        mainFrame.setLayout(null);
        mainFrame.setSize(1050,625);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //set content to background image
        mainFrame.setContentPane(mainBackground);
        mainFrame.setResizable(false);

        //set main background layout to null
        mainBackground.setLayout(null);


        //set menu panel layout to grid layout
        menuPanel.setLayout(new GridLayout(4,1,5,5));

        //manually set bounds for each component

        //setting button
        settingButton.setBounds(15,530,100,55);
        //username label
        usernameLabel.setBounds(80,30,150,40);
        //change username button
        changeUsername.setBounds(40,100,220,40);
        changeUsername.setVisible(false);

        //add buttons to menu panel
        menuPanel.add(newGameButton);
        menuPanel.add(loadButton);
        menuPanel.add(rankingButton);
        menuPanel.add(quitButton);
        //set menu panel bounds
        menuPanel.setBounds(525,130,290,320);
        //add color to menu panel
        menuPanel.setBackground(Color.darkGray);


        //add menu panel and other components
        mainBackground.add(menuPanel);
        mainBackground.add(settingButton);
        mainBackground.add(usernameLabel);
        mainBackground.add(changeUsername);
        mainBackground.revalidate();

        //add mouse listener to username label
        //change username label appears whenever mouse is clicked
        usernameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeUsername.setVisible(true);

            }
        });

        //add mouse listener to change username label
        //rename username frame appears whenever mouse is clicked
        changeUsername.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                user.renameUserFrame();
            }
        });

        //game ends whenever this button is clicked on
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        //if its the first click, setting UI is created
        //more than one click just shows the same frame again with changes that were made
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(clicks == 0)
                    settings.createSettings();
                else
                    settings.getSettingsFrame().setVisible(true);

                clicks++;
            }
        });

        //show main frame
        mainFrame.setVisible(true);

    }

    /**
     * This method sets features like color and font for all labels in the pages.
     */

    public void makeLabels(){
        //create a new font
        Font font = new Font("new",Font.ITALIC,20);

        //set username label features
        usernameLabel.setFont(font);
        usernameLabel.setBackground(new Color(51,0,0));
        usernameLabel.setOpaque(true);
        usernameLabel.setForeground(Color.WHITE);

        //set change username label features
        changeUsername.setBackground(new Color(51,0,0));
        changeUsername.setBorder(new LineBorder(new Color(255,255,100),5));
        changeUsername.setFont(new Font("change",Font.ITALIC,16));
        changeUsername.setForeground(Color.WHITE);
        changeUsername.setOpaque(true);

        //whenever mouse enters or exists the foreground color changes
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

    /**
     * This method gets a button and sets features such as color and font.
     * @param button given button that needs to be changed
     */
    public void makeButtons(JButton button){
        //create a new font
        Font font = new Font("new",Font.ITALIC,20);

        //set new game button features
        newGameButton.setBackground(Color.darkGray);
        newGameButton.setFont(new Font("new",Font.ITALIC,32));
        newGameButton.setForeground(Color.WHITE);
        //change the color
        changeColor(newGameButton);

        //set button features
        button.setBackground(Color.darkGray);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        changeColor(button);

    }

    /**
     * This method gets a button and add changing color feature to it
     * @param button given button
     */
    public void changeColor(JButton button){
        //whenever mouse enters or exits the color changes
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

    /**
     * get settings object.
     * @return settings field
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * get newGame button.
     * @return newGame field.
     */
    public JButton getNewGameButton() {
        return newGameButton;
    }

    /**
     * This method closes main frame.
     */
    public void disable()
    {
        mainFrame.setVisible(false);
    }

    /**
     * gets user UI object.
     * @return user field
     */
    public User getUser() {
        return user;
    }

    /**
     * This method enables logging in
     */
    public void login(){
        createMainMenu();
        startFrame.setVisible(false);
        user.getUserFrame().setVisible(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * gets username label.
     * @return username label field
     */
    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    /**
     * gets ranking button.
     * @return ranking button
     */
    public JButton getRankingButton() {
        return rankingButton;
    }

    /**
     * gets scoreBoard object.
     * @return scoreBoard field
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * this method creates a new loadBoard.
     */
    public void showLoadBoard()
    {
       loadBoard = new LoadBoard( getUsernameLabel().getText());
    }

    /**
     * gets load game button.
     * @return load button
     */
    public JButton getLoadButton() {
        return loadButton;
    }

    /**
     * gets loadBoard object.
     * @return loadBoard.
     */
    public LoadBoard getLoadBoard() {
        return loadBoard;
    }

    /**
     * gets a String which is the game number and sets it here.
     * @param gameNumber given game number
     */
    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }

    /**
     * gets game number.
     * @return game number field
     */
    public String getGameNumber() {
        return gameNumber;
    }
}

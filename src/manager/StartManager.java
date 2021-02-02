package manager;

import game.template.bufferstrategy.GameAudio;
import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.GameLoop;
import game.template.bufferstrategy.ThreadPool;
import gui.MainMenu;
import gui.PauseMenu;
import network.Server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class controls the game.
 * an object of server class is created here.
 * the game starts.
 * access to different parts of UI is available from here.
 * @version 1.0 2021
 * @authors Elaheh Akbari and Tanya Djavaherpour
 */
public class StartManager {
    //create new main menu
    private static MainMenu mainMenu = new MainMenu();

    //create a string to keep game type
    private static String type;

    //create string to keep game mode
    private static String mode;

    //pause menu field
    private PauseMenu pauseMenu;

    //game frame field
    private  static GameFrame frame;

    //flag to check whether to start a new gam
    private static int flag;

    //audio field
    private static GameAudio audio;

    //string to keep music status
    private static String music;

    //string to keep game number (name of the game)
    private static String gameNum;

    //create a new server object
    private static final Server server = new Server();

    /**
     * constructor to start server
     */
    public StartManager()
    {
        server.waitForClient();
    }

    /**
     * this method updates all features from UI every time its called
     */
    public static void update()
    {
        //create a gameAudio object
        audio = new GameAudio();
        if(!music.equals("off"))
            audio.playMenu(true);
        if(flag>0)
        {
            //create and show main menu
            mainMenu.createMainMenu();
        }

        //get type from settings and set it here
        type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();

        //get mode from settings and set it here
        mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();

        //get music from settings and set it here
        music = mainMenu.getSettings().getSoundButton().getText().toLowerCase();

        //call select method
        select();

        //start a new game
        if(flag == 0)
            startNewGame();
    }

    /**
     * this method closes game frame
     */
    public static void leave()
    {
        frame.setVisible(false);
    }

    /**
     * this method adds action listener to settings ok button
     * mode and music are selected here.
     */
    public static void select()
    {
        mainMenu.getSettings().getOkButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
                mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
                music = mainMenu.getSettings().getSoundButton().getText().toLowerCase();
                if(music.equals("off"))
                {
                    audio.playMenu(false);
                }
            }
        });
    }

    /**
     * This method starts a new game
     * whenever new game button is pressed from main menu.
     */
    public static void startNewGame()
    {
        mainMenu.getNewGameButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainMenu.disable();
                startGame();
            }
        });
    }

    /**
     * This method creates a game loop and starts the game.
     */
    public static void startGame()
    {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //get username from main menu
                String userName = mainMenu.getUsernameLabel().getText();
                gameNum = mainMenu.getGameNumber();
                flag++;
                if(!music.equals("off"))
                    audio.playMenu(false);

                //create new game frame
                frame = new GameFrame("Plants Vs. Zombies !", mode, type);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame,  mode, type, music, userName, gameNum);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }

    /**
     * gets main menu.
     * @return main menu field
     */
    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    /**
     * gets music.
     * @return music field.
     */
    public static String getMusic() {
        return music;
    }

    /**
     * gets an string and sets music field.
     * @param music given string
     */
    public static void setMusic(String music) {
        StartManager.music = music;
    }

    /**
     * gets server.
     * @return server field.
     */
    public static Server getServer() {
        return server;
    }

}
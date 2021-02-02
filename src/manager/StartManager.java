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
    private static MainMenu mainMenu = new MainMenu();
    private static String type;
    private static String mode;
    private PauseMenu pauseMenu;
    private  static GameFrame frame;
    private static int flag;
    private static GameAudio audio;
    private static String music;
    private static String gameNum;
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
        audio = new GameAudio();
        if(!music.equals("off"))
            audio.playMenu(true);
        if(flag>0)
        {
            mainMenu.createMainMenu();
        }
        type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
        mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
        music = mainMenu.getSettings().getSoundButton().getText().toLowerCase();
        select();
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
                String userName = mainMenu.getUsernameLabel().getText();
                gameNum = mainMenu.getGameNumber();
                flag++;
                if(!music.equals("off"))
                    audio.playMenu(false);
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
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

public class StartManager {
    private static MainMenu mainMenu = new MainMenu();
    private static String type;
    private static String mode;
    private PauseMenu pauseMenu;
    private  static GameFrame frame;
    private static int flag;
    private static GameAudio audio;
    //TODO
    private static String music;

    private Server server;

    public StartManager()
    {
        server = new Server();
        //TODO
        server.waitForClient();
 //       music = "on";
 //       mainMenu = new MainMenu();
 //       mainMenu.createStartGUI();
        //       update();
    }
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
        //TODO
        music = mainMenu.getSettings().getSoundButton().getText().toLowerCase();
        select();
        if(flag == 0)
            startNewGame();
    }
    public static void leave()
    {
        frame.setVisible(false);
    }
    public static void select()
    {
        mainMenu.getSettings().getOkButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
                mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
                //TODO
                music = mainMenu.getSettings().getSoundButton().getText().toLowerCase();
                if(music.equals("off"))
                {
                    audio.playMenu(false);
                    System.out.println("***");
                }
            }
        });
    }
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

    public static void startGame()
    {
        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                flag++;
                //TODO
                if(!music.equals("off"))
                    audio.playMenu(false);
                frame = new GameFrame("Plants Vs. Zombies !", mode, type);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                //TODO
                GameLoop game = new GameLoop(frame,  mode, type, music);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }

    public static MainMenu getMainMenu() {
        return mainMenu;
    }

    public static String getMusic() {
        return music;
    }

    public static void setMusic(String music) {
        StartManager.music = music;
    }
}

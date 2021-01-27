package manager;

import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.GameLoop;
import game.template.bufferstrategy.ThreadPool;
import gui.Background;
import gui.MainMenu;
import gui.PauseMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartManager {
    private static MainMenu mainMenu;
    private static String type;
    private static String mode;
    private PauseMenu pauseMenu;
//    private static boolean leave;
    private  static GameFrame frame;
//    private static GameLoop game;
    private static int flag;

    public StartManager()
    {
        mainMenu = new MainMenu();
//        mainMenu.setStartBackground();
        mainMenu.createStartGUI();
        update();
    }
    public static void update()
    {
//        leave = false;
        System.out.println(flag);
        if(flag>0)
        {
//            frame.setVisible(false);
            mainMenu.createMainMenu();
        }
        type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
        mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
        select();
        if(flag == 0)
            startNewGame();
    }
    public static void leave()
    {
        frame.setVisible(false);
        System.out.println("leave");
    }
    public static void select()
    {
        mainMenu.getSettings().getOkButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
                mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
                System.out.println(type);
                System.out.println(mode);
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
                frame = new GameFrame("Plants Vs. Zombies !", mode, type);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame,  mode, type);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }
}

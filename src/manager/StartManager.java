package manager;

import game.template.bufferstrategy.GameFrame;
import game.template.bufferstrategy.GameLoop;
import game.template.bufferstrategy.ThreadPool;
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
    private static boolean leave;

    public StartManager()
    {
        leave = false;
        update();
//        while (leave)
        {
//            mainMenu = new MainMenu();
//            type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
//            mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
//            select();
//            startNewGame();
        }
    }
    public static void update()
    {
        leave = false;
        mainMenu = new MainMenu();
        type = mainMenu.getSettings().getTypeButton().getText().toLowerCase();
        mode = mainMenu.getSettings().getModeButton().getText().toLowerCase();
        select();
        startNewGame();
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
        System.out.println("***");
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
                GameFrame frame = new GameFrame("Plants Vs. Zombies !", mode, type);
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame,  mode, type);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
                System.out.println(game.isLeave());
                leave = game.isLeave();
            }
        });
//        if(leave)
//            update();
    }
}

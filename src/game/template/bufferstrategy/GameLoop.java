/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.memory.Reload;
import game.memory.Save;
import gui.GameOver;
import gui.PauseMenu;
import manager.StartManager;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods
 * in the while loop (update() and render()) should be
 * long running! Both must execute very quickly, without
 * any waiting and blocking!
 *
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;

    private GameFrame canvas;
    private GameState state;
    private String type;
    private String timeType;
    //TODO
    private String music;
    private PauseMenu pauseMenu;
    private GameAudio audio;
    private boolean leave;

    public GameLoop(GameFrame frame, String type, String timeType, String music) {
        leave = false;
        this.type = type;
        this.timeType = timeType;
        //TODO
        this.music = music;
        canvas = frame;
//        pauseMenu = new PauseMenu();
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        // Perform all initializations ...
        state = new GameState(timeType, timeType);
//        Reload reload = new Reload(state, canvas, "Qoli");
//        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        boolean gameOver = false;
        pauseMenu = new PauseMenu();
        audio = new GameAudio();
        //TODO
        while (!gameOver && !leave && System.currentTimeMillis() - state.getStartTime() < 480000) {
            pauseMenu = new PauseMenu();
            while (state.getMenu() && !pauseMenu.isExitClicked()) {
                pauseMenu.start();
                while (!pauseMenu.isResumeClicked() && !pauseMenu.isExitClicked())
                    pauseMenu.getPauseFrame().setVisible(true);
                if(pauseMenu.isResumeClicked()) {
                    state.setMenu(false);
                    pauseMenu.falseResumeButton(); }
                if(pauseMenu.isSaveClicked()) {
                    pauseMenu.falseSaveButton();
                    Save save;
                    save = new Save(state); }
                while (pauseMenu.isExitClicked() && !leave) {
                    pauseMenu.askUser();
                    state.setMenu(true);
                    if(pauseMenu.isLeaveClicked()) {
                        leave = true;
                        pauseMenu.getAskFrame().setVisible(false);
                        pauseMenu.getPauseFrame().setVisible(false); }
                }
                if(!pauseMenu.isExitClicked())
                    pauseMenu.getAskFrame().setVisible(false);
            }
            if(!state.getMenu()) {
                if(!music.equals("off"))
                    audio.playZombiesComing(state);
                try {
                    long start = System.currentTimeMillis();
                    //
                    state.update();
                    canvas.render(state);
                    //
                    long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
                    if (delay > 0)
                        Thread.sleep(delay);
                } catch (InterruptedException ex) {
                }
            }
            gameOver = state.isGameOver();
            if(!music.equals("off"))
                audio.playBackGround(state, leave, gameOver);

        }
        //TODO
        if(gameOver || System.currentTimeMillis() - state.getStartTime() >= 480000)
        {
            GameOver end = new GameOver();
            if(!music.equals("off"))
                audio.playEndGame(true);
            int flag = 0;
            if(gameOver)
                end.setType("gameOver");
            else if(System.currentTimeMillis() - state.getStartTime() >= 480000)
                end.setType("endOfGame");
            end.start();
            end.getLeaveButton().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!music.equals("off"))
                        audio.playEndGame(false);
                    end.closeFrame();
                    backToMenu();
                }
            });
        }
        else if(leave)
        {
            backToMenu();
        }
    }
    //TODO
    /**
     * Closes gameFrame and shows main menu again.
     */
    private void backToMenu()
    {
        StartManager.leave();
        StartManager.update();
        leave = false;
    }
}

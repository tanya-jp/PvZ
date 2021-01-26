/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import gui.PauseMenu;
import manager.StartManager;

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
    private PauseMenu pauseMenu;
    private boolean leave;

    public GameLoop(GameFrame frame, String type, String timeType) {
        leave = false;
        this.type = type;
        this.timeType = timeType;
        canvas = frame;
//        pauseMenu = new PauseMenu();
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        // Perform all initializations ...
        state = new GameState(type, timeType);
//        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    @Override
    public void run() {
        boolean gameOver = state.isGameOver();
        pauseMenu = new PauseMenu();
        while (!gameOver && !leave) {
            pauseMenu = new PauseMenu();
            while (state.getMenu() && !pauseMenu.isExitClicked())
            {
                pauseMenu.start();
                while (!pauseMenu.isResumeClicked() && !pauseMenu.isExitClicked())
                    pauseMenu.getPauseFrame().setVisible(true);
                if(pauseMenu.isResumeClicked())
                {
                    state.setMenu(false);
                    pauseMenu.falseResumeButton();
                }
                while (pauseMenu.isExitClicked() && !leave)
                {
                    pauseMenu.askUser();
                    state.setMenu(true);
                    if(pauseMenu.isLeaveClicked())
                    {
                        leave = true;
                        pauseMenu.getAskFrame().setVisible(false);
                        pauseMenu.getPauseFrame().setVisible(false);
                    }
                }
                if(!pauseMenu.isExitClicked())
                {
                    pauseMenu.getAskFrame().setVisible(false);
                }
            }
            if(!state.getMenu())
            {
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
                gameOver = state.isGameOver();
            }
        }
        StartManager.update();
    }

    public boolean isLeave() {
        return leave;
    }
}
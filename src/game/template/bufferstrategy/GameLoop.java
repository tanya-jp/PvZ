/*** In The Name of Allah ***/
package game.template.bufferstrategy;

import game.memory.Reload;
import game.memory.Save;
import game.memory.SaveFinishedGame;
import gui.GameOver;
import gui.PauseMenu;
import manager.StartManager;
import utils.FileUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
 * @author Seyed Mohammad Ghaffarian, Tanya Djavaherpour, Elaheh Akbari
 *
 */
public class GameLoop implements Runnable {

    /**
     * Frame Per Second.
     * Higher is better, but any value above 24 is fine.
     */
    public static final int FPS = 30;
    //game backGround
    private GameFrame canvas;
    private GameState state;
    private String type;
    private String timeType;
    private String music;
    private PauseMenu pauseMenu;
    private GameAudio audio;
    private String userName;
    private boolean leave;
    private boolean save;
    private String gameNum;
    private Reload reload;
    /**
     * Creates a new game loop
     * @param frame as frame of the game
     * @param type , normal / hard
     * @param timeType , day / night
     * @param music , music should be played or not
     * @param userName as player's username
     * @param gameNum as number of the selected game
     */
    public GameLoop(GameFrame frame, String type, String timeType, String music, String userName,
                    String gameNum) {
        leave = false;
        save = false;
        this.type = type;
        this.timeType = timeType;
        this.userName = userName;
        this.music = music;
        this.gameNum = gameNum;
        canvas = frame;
    }

    /**
     * This must be called before the game loop starts.
     */
    public void init() {
        // Perform all initializations ...
        String score = FileUtils.scanByLineNumber(new File(".\\users\\"+userName+"\\score.txt"), 1);
        state = new GameState(type, timeType, score);
        //if it is a saves game
       if(gameNum != null)
            reload = new Reload(state, canvas, userName + "\\" + gameNum);
//        canvas.addKeyListener(state.getKeyListener());
        canvas.addMouseListener(state.getMouseListener());
        canvas.addMouseMotionListener(state.getMouseMotionListener());
    }

    /**
     * Runs the game until player leaves, loses or wins
     */
    @Override
    public void run() {
        boolean gameOver = false;
        pauseMenu = new PauseMenu();
        audio = new GameAudio();
        //game loop starts until player leaves, loses or wins
        while (!gameOver && !leave && System.currentTimeMillis() - state.getStartTime() < 480000) {
            //shows pause menu
            pauseMenu = new PauseMenu();
            while (state.getMenu() && !pauseMenu.isExitClicked()) {
                pauseMenu.start();
                while (!pauseMenu.isResumeClicked() && !pauseMenu.isExitClicked())
                    pauseMenu.getPauseFrame().setVisible(true);
                if(pauseMenu.isResumeClicked()) {
                    state.setMenu(false);
                    pauseMenu.falseResumeButton(); }
                //saves the game
                if(pauseMenu.isSaveClicked()) {
                    pauseMenu.falseSaveButton();
                    Save save;
                    save = new Save(state, userName, "notFinished", gameNum);
                    gameNum = save.saveInformation();
                    }
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
        //game finished
        if(gameOver || System.currentTimeMillis() - state.getStartTime() >= 480000)
        {
            GameOver end = new GameOver();
            SaveFinishedGame save;
            if(!music.equals("off"))
                audio.playEndGame(true);
            int flag = 0;
            //lost
            if(gameOver)
            {
                end.setType("gameOver");
                save = new SaveFinishedGame(state, userName, "gameOver", gameNum);
                if(type.equals("normal"))
                    save.updateScore(-1);
                else
                    save.updateScore(-3);
                save.updateNetworkFile();
            }
            //won
            else if(System.currentTimeMillis() - state.getStartTime() >= 480000)
            {
                end.setType("endOfGame");
                save = new SaveFinishedGame(state, userName, "endOfGame", gameNum);
                if(type.equals("normal"))
                    save.updateScore(+3);
                else
                    save.updateScore(+10);
                save.updateNetworkFile();
            }
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
        //left
        else if(leave)
        {
            backToMenu();
        }
    }
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

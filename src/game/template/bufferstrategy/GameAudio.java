package game.template.bufferstrategy;

import player.MusicPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class handles musics of the game, plays music of menu when game starts,
 * plays music of the background, music of "zombies are coming" and music of end of game after losing or winning.
 * @version 1.0 2021
 * @authors Tanya Djavaherpour, Elaheh Akbari
 */
public class GameAudio {
    //path of clips
    private String zombiesComing;
    private String background;
    private String menu;
    private String gameEnd;
    //zombies state
    private boolean zombiesComingState;
    //background state
    private boolean backGroundState;
    private boolean menuState;
    //playing clip
    private Clip backGroundClip;
    AudioInputStream audioInputStream;
    /**
     * Constructs a new gameAudio and sets music links.
     */
    public GameAudio()
    {
        zombiesComing = ".\\PVS Design Kit\\sounds\\zombies_coming.wav";
        background = ".\\PVS Design Kit\\sounds\\background.wav";
        menu = ".\\PVS Design Kit\\sounds\\menu.wav";
        gameEnd = ".\\PVS Design Kit\\sounds\\game_end.wav";
        zombiesComingState = false;
        backGroundState = false;
        menuState = false;
        try {
            setBackground();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets background music
     * @throws IOException as exception
     * @throws UnsupportedAudioFileException as exception
     * @throws LineUnavailableException as exception
     */
    public void setBackground()throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(background).getAbsoluteFile());
        backGroundClip = AudioSystem.getClip();

        backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Plays and stops background music.
     * Plays when game starts, stops and closes the clip when game finished or player leaves the game
     * @param state as gameState
     * @param leave if player leaves the game this will be true.
     * @param gameOver if player loses the game this will be true.
     */
    public void playBackGround(GameState state, boolean leave, boolean gameOver) {
        //starts playing
        if (System.currentTimeMillis() - state.getStartTime() < 480000 && !gameOver
                && !leave && !backGroundState)
        {
            try {
                backGroundClip.open(audioInputStream);
                backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            backGroundClip.start();
            backGroundState = true;
        }
        //stops playing
        else if( (System.currentTimeMillis() - state.getStartTime() >= 480000 || gameOver
                || leave) && backGroundState)
        {
            backGroundClip.close();
        }
    }
    /**
     * When Zombies are coming plays the proper clip
     * @param state as game state
     */
    public void playZombiesComing(GameState state) {
        //starts playing when it is its time
        if (System.currentTimeMillis() - state.getStartTime() > 40090 &&
                System.currentTimeMillis() - state.getStartTime() < 40090 + 5000 && !zombiesComingState) {
            try {
                MusicPlayer.play(zombiesComing);
                zombiesComingState = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        //stops playing
        else if (System.currentTimeMillis() - state.getStartTime() > 40090 + 5000 && zombiesComingState)
        {
            MusicPlayer.stopPlaying();
            zombiesComingState = false;
        }
    }
    /**
     * Plays music of the menu
     * @param play: when it is true, it means that music should be played
     *            and when it is false it means that the music should be stopped
     */
    public void playMenu(boolean play)
    {
        //starts playing
        if(play)
        {
            try {
                MusicPlayer.play(menu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        //stops playing and closes the clip
        else
            MusicPlayer.stopPlaying();
    }
    /**
     * when player loses or wins, plays the end game music
     * @param play: true when game finished,
     *            false when player clicks main menu button
     */
    public void playEndGame(boolean play)
    {
        //starts playing
        if(play)
        {
            try {
                MusicPlayer.play(gameEnd);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        //stops playing and closes the clip
        else
            MusicPlayer.stopPlaying();
    }
}

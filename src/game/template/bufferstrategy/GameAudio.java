package game.template.bufferstrategy;

import player.MusicPlayer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class GameAudio {
    private String zombiesComing;
    private String background;
    private String menu;
    private String gameEnd;
    private boolean zombiesComingState;
    private boolean backGroundState;
    private boolean menuState;
    private Clip backGroundClip;
    AudioInputStream audioInputStream;
    public GameAudio()
    {
        zombiesComing = ".\\PVS Design Kit\\sounds\\zombies_coming.wav";
        background = ".\\PVS Design Kit\\sounds\\background.wav";
        menu = ".\\PVS Design Kit\\sounds\\menu.wav";
        //TODO correct the path
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

    public void setBackground()throws IOException, UnsupportedAudioFileException, LineUnavailableException
    {
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(background).getAbsoluteFile());
        backGroundClip = AudioSystem.getClip();

        backGroundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void playBackGround(GameState state, boolean leave, boolean gameOver) {
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
        else if( (System.currentTimeMillis() - state.getStartTime() >= 480000 || gameOver
                || leave) && backGroundState)
        {
            backGroundClip.close();
        }
    }
    public void playZombiesComing(GameState state) {
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
        else if (System.currentTimeMillis() - state.getStartTime() > 40090 + 5000 && zombiesComingState)
        {
            MusicPlayer.stopPlaying();
            zombiesComingState = false;
        }
    }
    public void playMenu(boolean play)
    {
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
        else
            MusicPlayer.stopPlaying();
    }
    //TODO
    public void playEndGame(boolean play)
    {
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
        else
            MusicPlayer.stopPlaying();
    }
}

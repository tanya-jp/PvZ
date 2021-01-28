package game.template.bufferstrategy;

import player.MusicPlayer;

import javax.sound.sampled.*;
import java.io.IOException;

public class GameAudio {
    private String zombiesComing;
    private String background;
    private String menu;
    private String gameEnd;
    private boolean zombiesComingState;
    private boolean backGroundState;
    private boolean menuState;
    public GameAudio()
    {
        zombiesComing = ".\\PVS Design Kit\\sounds\\zombies_coming.wav";
        background = ".\\PVS Design Kit\\sounds\\background.wav";
        menu = ".\\PVS Design Kit\\sounds\\menu.wav";
        gameEnd = ".\\PVS Design Kit\\game_end\\menu.wav";
        zombiesComingState = false;
        backGroundState = false;
        menuState = false;
    }

    public void playBackGround(GameState state, boolean leave, boolean gameOver) {
    if (System.currentTimeMillis() - state.getStartTime() < 480000 && !gameOver
                && !leave && !backGroundState)
        {
            try {
                MusicPlayer.play(background);
                backGroundState = true;
                System.out.println("(((((");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        else if( (System.currentTimeMillis() - state.getStartTime() >= 480000 || gameOver
                || leave) && backGroundState)
        {
            MusicPlayer.stopPlaying();
            System.out.println(")))))))))");
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
}

package game.template.bufferstrategy;

import player.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class GameAudio {
    private String zombiesComing;
    private String background;
    private boolean zombiesComingState;
    public GameAudio()
    {
        zombiesComing = ".\\PVS Design Kit\\sounds\\zombies_coming.wav";
        background = ".\\PVS Design Kit\\sounds\\background.wav";
        zombiesComingState = false;
    }
    public void playStarter(GameState state)
    {
           try {
               if(!state.isGameOver())
                AudioPlayer.play(background);
           } catch (UnsupportedAudioFileException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (LineUnavailableException e) {
               e.printStackTrace();
           }
    }
    public void playZombiesComing(GameState state) {
        if (System.currentTimeMillis() - state.getStartTime() > 40090 &&
                System.currentTimeMillis() - state.getStartTime() < 40090 + 5000 && !zombiesComingState) {
            try {
                AudioPlayer.play(zombiesComing);
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
                AudioPlayer.stopPlaying();
    }
    public void play(GameState state) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(System.currentTimeMillis() - state.getStartTime() > 0 &&
                System.currentTimeMillis() - state.getStartTime() < 50000)
        {
            AudioPlayer.play(background);
            System.out.println("play play");
        }
        if(System.currentTimeMillis() - state.getStartTime() > 40090 &&
                System.currentTimeMillis() - state.getStartTime() < 40090 + 5000)
            AudioPlayer.play(zombiesComing);
    }
}

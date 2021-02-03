package player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class plays and stops the music.
 * @version 1.0 2021
 * @author Elaheh akbari, Tanya Djavaherpour
 */
public class MusicPlayer {
    //clip that should be played
    private static Clip clip;
    /**
     * Constructs a new player
     */
    public MusicPlayer()
    {
        clip = null;
    }

    /**
     * Plays the clip if it is possible
     * @param filePath as path of the clip
     * @throws IOException as exception
     * @throws UnsupportedAudioFileException as exception
     * @throws LineUnavailableException as exception
     */
    public static void play(String filePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        Clip clip;
        AudioInputStream audioInputStream;
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
    /**
     * Stops playing and closes the clip
     */
    public static  void stopPlaying()
    {
        clip.stop();
        clip.close();
    }
    /**
     * Returns the clip that is being played
     */
    public static Clip getClip() {
        return clip;
    }
}

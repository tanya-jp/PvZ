package player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private static Clip clip;
    public MusicPlayer()
    {
        clip = null;
    }

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

    public static  void stopPlaying()
    {
        clip.stop();
        clip.close();
    }
    public static Clip getClip() {
        return clip;
    }
}

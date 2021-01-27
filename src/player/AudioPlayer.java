package player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    private static Clip clip;
    public AudioPlayer()
    {

    }

    public static void play(String filePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Long currentFrame;
//        Clip clip;

        // current status of clip
        String status;

        AudioInputStream audioInputStream;
//        String filePath = ".\\PVS Design Kit\\sounds\\atebrains.wav";
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
    }
    public static Clip getClip() {
        return clip;
    }
}

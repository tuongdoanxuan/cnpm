package Model;

import javax.sound.sampled.*;
import java.io.File;

public class BackgroundMusicPlayer {
    private Clip clip;

    public void playLoop(String filepath) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(filepath));
            clip = AudioSystem.getClip();
            clip.open(audio);
            
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f); // Giảm âm lượng (đơn vị: decibel)
            
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}


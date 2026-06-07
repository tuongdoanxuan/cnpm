package Model;

public class AudioAdapter implements GameAudio {
    private SoundEffectPlayer sfxPlayer;
    private BackgroundMusicPlayer musicPlayer;

    public AudioAdapter(SoundEffectPlayer sfx, BackgroundMusicPlayer music) {
        this.sfxPlayer = sfx;
        this.musicPlayer = music;
    }

    @Override
    public void playJumpSound() {
        sfxPlayer.playSound("data/sfx_wing.wav");
    }

    @Override
    public void playBackgroundMusic() {
        musicPlayer.playLoop("data/music.wav");
    }

    @Override
    public void stopBackgroundMusic() {
        musicPlayer.stop();
    }
}


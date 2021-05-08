package com.uninorte.base.sound;

import com.uninorte.base.game.gfx.ContentLoader;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Sound {
    public static final String BACKGROUND = "background";
    public static final String SHOTS = "shots";
    public static final String ALIEN = "alien";
    public static final String GAMEOVER = "gameover";

    private final HashMap<String, Audio> sounds;

    private boolean debug;

    public Sound(boolean debug) {
        this.sounds = new HashMap<>();
        this.debug = debug;
    }

    public void add(String alias, String soundPath) {
        try {
            this.sounds.put(alias, new Audio(0.5f, soundPath));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void play(String alias) throws Exception {
        Audio a = getAudio(alias);

        a.clipSound.setFramePosition(0);
        a.clipSound.loop(Clip.LOOP_CONTINUOUSLY);
        a.clipSound.start();
    }

    public float getVolume(String alias) throws Exception {
        Audio a = getAudio(alias);
        return a.getVolume();
    }

    public void setVolume(String alias, float volume) throws Exception {
        Audio a = getAudio(alias);

        if (debug)
            System.out.printf("[DEBUG] %s -> %.2f\n", alias, volume);
        a.setVolume(volume);
    }

    public boolean isMuted(String alias) throws Exception {
        Audio a = getAudio(alias);
        return a.isMuted;
    }

    public void setMuted(String alias) throws Exception {
        Audio a = getAudio(alias);
        a.mute();
    }

    private Audio getAudio(String alias) throws Exception {
        Audio a = sounds.get(alias);
        if (a == null) {
            throw new Exception("sound with the alias " + alias + " not found");
        }

        return a;
    }

    private static class Audio {
        boolean isMuted;
        float currentVolume;
        Clip clipSound;
        String path;

        Audio(float currentVolume, String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
            this.path = path;
            this.isMuted = false;
            this.currentVolume = currentVolume;

            clipSound = AudioSystem.getClip();
            clipSound.open(AudioSystem.getAudioInputStream(ContentLoader.loadAudio(path)));

            setVolume(currentVolume);
        }

        void setVolume(float volume) {
            if (volume < 0f || volume > 1f)
                throw new IllegalArgumentException("Volume not valid: " + volume);

            FloatControl gainControl = (FloatControl) this.clipSound.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }

        float getVolume() {
            FloatControl gainControl = (FloatControl) clipSound.getControl(FloatControl.Type.MASTER_GAIN);
            return (float) Math.pow(10f, gainControl.getValue() / 20f);
        }

        void mute() {
            isMuted = !isMuted;
            BooleanControl muteControl = (BooleanControl) clipSound.getControl(BooleanControl.Type.MUTE);
            muteControl.setValue(isMuted);
        }
    }

}

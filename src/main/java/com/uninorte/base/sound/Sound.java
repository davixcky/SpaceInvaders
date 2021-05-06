package com.uninorte.base.sound;

import com.uninorte.base.game.gfx.ContentLoader;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;
import java.util.HashMap;

public class Sound {
    public static final String BACKGROUND = "background";
    public static final String SHOTS = "shots";
    public static final String ALIEN = "alien";
    public static final String GAMEOVER = "gameover";

    private final HashMap<String, Clip> sounds;

    public Sound() {
        this.sounds = new HashMap<>();
    }

    public void add(String alias, String soundPath) {
        try {
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(ContentLoader.loadAudio(soundPath)));
            c.loop(Clip.LOOP_CONTINUOUSLY);
            this.sounds.put(alias, c);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void play(String alias) throws Exception {
        Clip c = sounds.get(alias);
        if (c == null) {
            throw new Exception("sound with the alias " + alias + " not found");
        }

        System.out.println("Listening music");

        c.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                System.out.println("Sound: " + event.toString() + "\n" +
                        event.getFramePosition() + "\n" +
                        event.getLine());
            }
        });

        c.setFramePosition(0);
        c.start();
    }

}

package com.uninorte.base.display;

import com.uninorte.base.Filenames;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.input.Input;
import com.uninorte.base.sound.Sound;
import com.uninorte.base.views.Principal;
import com.uninorte.base.views.Settings;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Window {

    private JLayeredPane scenesPane;

    private JFrame frame;
    private JPanel backgroundPanel;
    private JLabel backgroundImage;

    private String title;
    private Dimension startDimensions;

    private Component actualComponent;

    private Input input;

    public static Principal principal;
    public static Settings settings;

    private Sound soundController;

    public Window(String title, Dimension startDimensions) {
        this.title = title;
        this.startDimensions = startDimensions;
        this.scenesPane = new JLayeredPane();
        this.input = new Input();
        this.soundController = new Sound();

        init();
        createScenes();

        principal = new Principal(this, soundController);
        settings = new Settings(this);

        this.soundController.add(Sound.BACKGROUND, "/sounds/background_DuaLipa.wav");

        try {
            this.soundController.play(Sound.BACKGROUND);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        principal.setToCurrentView();
    }

    private void init() {
        frame = new JFrame(title);
        frame.setSize(startDimensions);
        frame.setMinimumSize(startDimensions);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);
        frame.addKeyListener(input);

        backgroundPanel = new JPanel(new BorderLayout());
        backgroundImage = new JLabel();
        setBackgroundImage(Filenames.BACKGROUND1);
        backgroundPanel.add(backgroundImage);

        createScenes();
    }

    private void createScenes() {
        scenesPane = new JLayeredPane();
        scenesPane.setLayout(new OverlayLayout(scenesPane));
        scenesPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        frame.add(scenesPane);
    }

    private void setBackgroundImage(String path) {
        backgroundImage.setIcon(ContentLoader.loadImageGif(path));
    }

    public void changeScreenTo(Component c) {
        if (actualComponent != null) {
            scenesPane.remove(actualComponent);
        }

        scenesPane.add(c, JLayeredPane.PALETTE_LAYER);
        frame.setVisible(true);
        actualComponent = c;
    }

    public void close() {
        frame.dispose();
        System.exit(0);
    }

    public void setTitle(String newTitle) {
        title = newTitle;
        frame.setTitle(title);
    }
}

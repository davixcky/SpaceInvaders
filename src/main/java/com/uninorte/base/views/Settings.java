package com.uninorte.base.views;

import com.uninorte.base.Filenames;
import com.uninorte.base.display.Window;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.sound.Sound;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settings {

    private Window window;
    private Sound sound;

    private JPanel basePanel;

    private JLabel settingsLbl;
    private JLabel backgroundSoundLbl;
    private JLabel effectsSoundLbl;

    private JButton backgroundBtn;
    private JButton muteBackgroundBtn;
    private JButton muteEffectsBtn;
    private JButton backBtn;

    private JSlider backgroundSoundSlider;
    private JSlider effectsSoundsSlider;

    private int backgroundIndex = 0;

    public Settings(Window window, Sound sound) {
        this.window = window;
        this.sound = sound;
        init();
        setListeners();
    }

    private void init() {
        basePanel = new JPanel(new BorderLayout());
        basePanel.setOpaque(false);

        initializeLabels();
        initializeButtons();
        initializeSliders();

        createTopPanel();
        createBottomPanel();
        createMiddlePanel();

        changePreview();
    }

    private void setListeners() {
        backgroundBtn.addActionListener(e -> changeBackground());
        backBtn.addActionListener(e -> Window.principal.setToCurrentView());
        backgroundSoundSlider.addChangeListener(l -> setVolumeListener(Sound.BACKGROUND, backgroundSoundSlider.getValue()));
        muteBackgroundBtn.addActionListener(e -> changeMuteStatus(muteBackgroundBtn, Sound.BACKGROUND));
        effectsSoundsSlider.addChangeListener(l ->  {
            int currentValue = effectsSoundsSlider.getValue();

            setVolumeListener(Sound.ALIEN, currentValue);
            setVolumeListener(Sound.GAMEOVER, currentValue);
            setVolumeListener(Sound.SHOTS, currentValue);
        });
        muteEffectsBtn.addActionListener(l ->  {
            changeMuteStatus(muteEffectsBtn, Sound.ALIEN);

            try {
                sound.setMuted(Sound.GAMEOVER);
                sound.setMuted(Sound.SHOTS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void setToCurrentView() {
        window.setTitle("Settings");
        window.changeScreenTo(basePanel);
    }

    private void createTopPanel() {
        Helpers.createPanel(basePanel, BorderLayout.NORTH, BoxLayout.LINE_AXIS,
                settingsLbl,
                Box.createHorizontalGlue(),
                backBtn);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = Helpers.createPanel(basePanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(0, 60, 0, 0));

        bottomPanel.add(backgroundBtn, BorderLayout.EAST);
    }

    private void createMiddlePanel() {
        JPanel topMiddle = Helpers.createPanelNonRoot(BoxLayout.LINE_AXIS,
                backgroundSoundSlider,
                muteBackgroundBtn);
        topMiddle.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JPanel bottomMiddle = Helpers.createPanelNonRoot(BoxLayout.LINE_AXIS,
                effectsSoundsSlider,
                muteEffectsBtn);
        bottomMiddle.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        JPanel middlePanel = Helpers.createPanel(basePanel, BorderLayout.CENTER, BoxLayout.PAGE_AXIS,
                Box.createRigidArea(new Dimension(0, 20)),
                backgroundSoundLbl,
                topMiddle,
                Box.createRigidArea(new Dimension(0, 80)),
                effectsSoundLbl,
                bottomMiddle);

        middlePanel.setBorder(new EmptyBorder(0, 35, 0, 0));
        backgroundSoundLbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        effectsSoundLbl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
    }

    private void initializeButtons() {
        backBtn = Helpers.createButton("/returnArrow.png", new Dimension(70, 60));
        backgroundBtn = Helpers.createButton("/returnArrow.png", new Dimension(50, 50));
        muteBackgroundBtn = Helpers.createButton("/mutebtn.png", new Dimension(50, 50));
        muteEffectsBtn = Helpers.createButton("/mutebtn.png", new Dimension(50, 50));
    }

    private void initializeLabels() {
        settingsLbl = new JLabel("<html><h1>Settings Panel</h1></html>");
        settingsLbl.setFont(new Font("SEGOE UI",Font.ITALIC,12));
        settingsLbl.setForeground(Color.white);

        backgroundSoundLbl = new JLabel("<html><h2>Background Volume</h2></html>");
        backgroundSoundLbl.setFont(new Font("SansSerif Bold",Font.ITALIC,12));
        backgroundSoundLbl.setForeground(Color.white);

        effectsSoundLbl = new JLabel("<html><h2>Effects Volume</h2></html>");
        effectsSoundLbl.setFont(new Font("SansSerif Bold",Font.ITALIC,12));
        effectsSoundLbl.setForeground(Color.white);
    }

    private void initializeSliders() {
        try {
            backgroundSoundSlider = new JSlider(0, 100, (int) (sound.getVolume(Sound.BACKGROUND) * 100));
            effectsSoundsSlider = new JSlider(0, 100, (int) (sound.getVolume(Sound.BACKGROUND) * 100));
        } catch (Exception e) {
            e.printStackTrace();
        }

        backgroundSoundSlider.setMaximumSize(new Dimension(690, 40));
        effectsSoundsSlider.setMaximumSize(new Dimension(690, 40));

        setSlider(backgroundSoundSlider);
        setSlider(effectsSoundsSlider);
    }

    private void setSlider(JSlider effectsSoundsSlider) {
        effectsSoundsSlider.setPaintTicks(true);
        effectsSoundsSlider.setMajorTickSpacing(10);
        effectsSoundsSlider.setPaintTrack(true);
        effectsSoundsSlider.setPaintLabels(true);
        effectsSoundsSlider.setFont(new Font("SEGOE UI",Font.ITALIC,12));
        effectsSoundsSlider.setOpaque(false);
        effectsSoundsSlider.setForeground(Color.white);
    }

    private void setVolumeListener(String alias, int value) {
        try {
            sound.setVolume(alias, value / 100f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeBackground() {
        backgroundIndex++;

        if (backgroundIndex == Filenames.BACKGROUND_IMAGES.length) {
            backgroundIndex = 0;
        }

        changePreview();
        window.setBackgroundImage(Filenames.BACKGROUND_IMAGES[backgroundIndex]);
    }

    private void changePreview() {
        int nextPreview = backgroundIndex + 1;
        if (nextPreview >= Filenames.BACKGROUND_IMAGES.length) {
            nextPreview = 0;
        }

        backgroundBtn.setIcon(ContentLoader.loadImageGif(Filenames.BACKGROUND_IMAGES[nextPreview]));
    }

    private void changeMuteStatus(JButton target, String alias) {
        try {
            String path = sound.isMuted(alias) ? "/mutebtn.png" : "/unmutebtn.png";
            sound.setMuted(alias);
            target.setIcon(new ImageIcon(ContentLoader.loadImage(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


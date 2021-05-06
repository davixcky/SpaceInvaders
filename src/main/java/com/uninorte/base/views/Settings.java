package com.uninorte.base.views;

import com.uninorte.base.display.Window;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Settings {

    private Window window;

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

    public Settings(Window window) {
        this.window = window;
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
    }

    private void setListeners() {
        backBtn.addActionListener((e) -> Window.principal.setToCurrentView());
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
        backgroundBtn = Helpers.createButton("/returnArrow.png");
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
        backgroundSoundSlider = new JSlider(0, 100, 10);
        effectsSoundsSlider = new JSlider(0, 100, 10);

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
        effectsSoundsSlider.setSnapToTicks(true);
    }

}


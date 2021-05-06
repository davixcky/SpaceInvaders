package com.uninorte.base.display;

import com.uninorte.base.Filenames;
import com.uninorte.base.game.gfx.ImageLoader;
import com.uninorte.base.input.Input;
import com.uninorte.base.menus.Principal;
import com.uninorte.base.menus.Settings;

import javax.swing.*;
import java.awt.*;

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

    public Window(String title, Dimension startDimensions) {
        this.title = title;
        this.startDimensions = startDimensions;
        this.scenesPane = new JLayeredPane();
        this.input = new Input();

        init();
        createScenes();

        principal = new Principal(this);
        settings = new Settings(this);

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
        frame.setVisible(true);
    }

    private void createScenes() {
        scenesPane = new JLayeredPane();
        scenesPane.setLayout(new OverlayLayout(scenesPane));
        scenesPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        frame.add(scenesPane);
    }

    private void setBackgroundImage(String path) {
        backgroundImage.setIcon(ImageLoader.loadImageGif(path));
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
    }
}

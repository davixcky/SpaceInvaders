package com.uninorte.base.views;

import com.uninorte.base.display.Window;
import com.uninorte.base.game.Game;
import com.uninorte.base.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

public class Principal {
    private Window window;

    private JPanel basePanel;

    private JButton helpBtn;
    private JButton sourceBtn;
    private JButton closeBtn;
    private JButton settingsBtn;
    private JButton singleplayerBtn;
    private JButton multiplayerBtn;

    private JTextField nicknameInput;

    private JLabel titleLbl;

    private Sound sound;

    public Principal(Window window, Sound sound) {
        this.window = window;
        this.sound = sound;
        init();
        setListeners();
    }

    private void init() {
        basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout());
        basePanel.setOpaque(false);

        Dimension d1 = new Dimension(71, 60);
        Dimension d2 = new Dimension(180, 51);
        Dimension d3 = new Dimension(200, 51);
        Dimension d4 = new Dimension(80, 90);

        helpBtn = Helpers.createButton("/help.png", d1);
        sourceBtn = Helpers.createButton("/GIT.png", d1);
        closeBtn = Helpers.createButton("/exitIcon.png", d4);
        settingsBtn = Helpers.createButton("/settings.png", d4);
        singleplayerBtn = Helpers.createButton("/singleplayer.png", "/singleplayerHover.png", d2);
        multiplayerBtn = Helpers.createButton("/multiplayer.png", "/multiplayerHover.png", d3);

        nicknameInput = new JTextField();

        titleLbl = new JLabel();
        titleLbl.setText("<html><h1 align='CENTER'>Space Invaders</h1><h2 align='CENTER'>Remake</h2></html>");
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(new Font("SEGOE UI", Font.ITALIC, 12));

        createTopPanel();
        createMiddlePanel();
        createBottomPanel();
    }

    private void setListeners() {
        nicknameInput.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nicknameInput.getText().equals("NICKNAME"))
                    nicknameInput.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (nicknameInput.getText().equals(""))
                    nicknameInput.setText("NICKNAME");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (nicknameInput.getText().equals(""))
                    nicknameInput.setText("NICKNAME");
            }
        });
        closeBtn.addActionListener(e -> window.close());
        settingsBtn.addActionListener(e -> Window.settings.setToCurrentView());
        sourceBtn.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://github.com/Norte-invaders"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        helpBtn.addActionListener(e -> {
            try {
                sound.play(Sound.BACKGROUND);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        singleplayerBtn.addActionListener(e -> {
            window.game.start();
            window.stopFrame();
        });
    }

    public void setToCurrentView() {
        window.setTitle("Menu principal");
        window.changeScreenTo(basePanel);
    }

    private void createTopPanel() {
        Helpers.createPanel(basePanel, new Dimension(100, 60), BorderLayout.NORTH, BoxLayout.LINE_AXIS,
                sourceBtn,
                Box.createHorizontalGlue(),
                helpBtn);
    }

    private void createMiddlePanel() {
        nicknameInput.setText("NICKNAME");
        nicknameInput.setOpaque(false);
        nicknameInput.setForeground(Color.WHITE);
        nicknameInput.setHorizontalAlignment(JTextField.CENTER);
        nicknameInput.setPreferredSize(new Dimension(100, 30));
        nicknameInput.setMaximumSize(new Dimension(100, 30));
        nicknameInput.setAlignmentX(JTextField.CENTER_ALIGNMENT);

        titleLbl.setMaximumSize(new Dimension(172, 100));
        titleLbl.setPreferredSize(new Dimension(172, 100));
        titleLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        singleplayerBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        multiplayerBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);

        Helpers.createPanel(basePanel, new Dimension(100, 100), BorderLayout.CENTER, BoxLayout.PAGE_AXIS,
                Box.createVerticalGlue(),
                titleLbl,
                Box.createRigidArea(new Dimension(0, 60)),
                nicknameInput,
                Box.createRigidArea(new Dimension(0, 10)),
                singleplayerBtn,
                Box.createRigidArea(new Dimension(0, 10)),
                multiplayerBtn,
                Box.createVerticalGlue());
    }

    private void createBottomPanel() {
        Helpers.createPanel(basePanel, new Dimension(100, 70), BorderLayout.SOUTH, BoxLayout.LINE_AXIS,
                closeBtn,
                Box.createHorizontalGlue(),
                settingsBtn);
    }
}

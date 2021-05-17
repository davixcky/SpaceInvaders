package com.uninorte.base.game.display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Display {

    private JFrame frame;
    private Canvas gameCanvas;

    private String title;
    private Dimension windowSize;

    public Display(String title, Dimension windowSize) {
        this.title = title;
        this.windowSize = windowSize;

        createDisplay();
    }

    private void createDisplay() {
        createFrame();
        createCanvas();
    }

    private void createFrame() {
        frame = new JFrame(title);
        frame.setSize(windowSize);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int option = JOptionPane.showConfirmDialog(frame,
                        "Quiere salir del juego?", "Salir del juego?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (option== JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    frame.setVisible(true);
                }
            }
        });
    }

    private void createCanvas() {
        gameCanvas = new Canvas();
        gameCanvas.setPreferredSize(windowSize);
        gameCanvas.setMaximumSize(windowSize);
        gameCanvas.setMinimumSize(windowSize);
        gameCanvas.setFocusable(true);

        frame.add(gameCanvas);
        frame.pack();
    }

    public void addKeyListener(KeyListener l) {
        frame.addKeyListener(l);
    }

    public void addMouseListener(MouseListener l) {
        frame.addMouseListener(l);
        gameCanvas.addMouseListener(l);
    }

    public void addMouseMotionListener(MouseMotionListener l) {
        frame.addMouseMotionListener(l);
        gameCanvas.addMouseMotionListener(l);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Canvas getGameCanvas() {
        return gameCanvas;
    }

    public void close() {
        frame.dispose();
    }
}

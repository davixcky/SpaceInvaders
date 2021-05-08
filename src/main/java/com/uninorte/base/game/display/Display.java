package com.uninorte.base.game.display;

import com.uninorte.base.display.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
}

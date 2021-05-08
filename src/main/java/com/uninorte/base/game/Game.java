package com.uninorte.base.game;

import com.uninorte.base.display.Window;
import com.uninorte.base.game.States.GameState;
import com.uninorte.base.game.States.State;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.gfx.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private String title;
    private Dimension windowSize;

    private boolean running = false;
    private Thread gameThread;

    private BufferStrategy bs;
    private Graphics g;

    private Display display;

    private Handler handler;

    public State gameSate;


    public Game(String title, Dimension windowSize) {
        this.title = title;
        this.windowSize = windowSize;
    }

    private void init() {
        display = new Display(title, windowSize);
        Assets.init();

        handler = new Handler(this);
        gameSate = new GameState(handler);
        State.setCurrentState(gameSate);
    }

    private void update() {
        // Set mouse and key listeners


        if (State.getCurrentState() != null)
            State.getCurrentState().update();

    }

    private void render() {
        bs = display.getGameCanvas().getBufferStrategy();
        if (bs == null) {
            display.getGameCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, windowSize.width, windowSize.height);
        g.setColor(Color.white);
        g.fillRect(0, 0, windowSize.width, windowSize.height);

        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                update();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public synchronized void start() {
        // Exit if the game is already running
        if (running)
            return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop() {
        // Exit if the game is not running
        if (!running)
            return;

        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

}

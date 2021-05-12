package com.uninorte.base.game;

import com.uninorte.base.Filenames;
import com.uninorte.base.display.Window;
import com.uninorte.base.game.States.GameState;
import com.uninorte.base.game.States.State;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.input.KeyManager;

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

    private KeyManager keyManager;

    private Handler handler;

    public State gameSate;

    private Image background;


    public Game(String title, Dimension windowSize) {
        this.title = title;
        this.windowSize = windowSize;

        keyManager = new KeyManager();
    }

    private void init() {
        display = new Display(title, windowSize);
        display.getFrame().addKeyListener(keyManager);
        display.getGameCanvas().addKeyListener(keyManager);
        Assets.init();

        handler = new Handler(this);
        gameSate = new GameState(handler);
        State.setCurrentState(gameSate);

        background = new ImageIcon(ContentLoader.loadImage(Filenames.BACKGROUND_IMAGES[2])).getImage();
    }

    private void update() {
        // Set mouse and key listeners
        keyManager.tick();

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
        g.drawImage(background, 0, 0,  windowSize.width, windowSize.height, null);

        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000d / 60d;

        int frames = 0;
        int ticks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        boolean shouldRender = true;

        init();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                ticks++;
                update();
                delta--;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (shouldRender) {
                frames++;
                render();

            }

            if (System.currentTimeMillis() - lastTimer > 1000) {
//                System.out.println(frames + " " + ticks);
                frames = 0;
                lastTimer += 1000;
                ticks = 0;
            }
        }
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

    public KeyManager getKeyManager() {
        return keyManager;
    }
}

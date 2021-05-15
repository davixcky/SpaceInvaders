package com.uninorte.base.game;

import com.uninorte.base.Filenames;
import com.uninorte.base.game.input.MouseManager;
import com.uninorte.base.game.states.GameOverState;
import com.uninorte.base.game.states.GameState;
import com.uninorte.base.game.states.State;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.game.input.KeyManager;

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
    private MouseManager mouseManager;

    private Handler handler;

    public State gameSate;
    public State gameOverState;

    private Image background;


    public Game(String title, Dimension windowSize) {
        this.title = title;
        this.windowSize = windowSize;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init() {
        display = new Display(title, windowSize);
        display.getFrame().addKeyListener(keyManager);
        display.getGameCanvas().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getGameCanvas().addMouseListener(mouseManager);
        display.getGameCanvas().addMouseMotionListener(mouseManager);
        Assets.init();

        handler = new Handler(this);
        gameSate = new GameState(handler);
        gameOverState = new GameOverState(handler);
        State.setCurrentState(gameSate);

        background = new ImageIcon(ContentLoader.loadImage(Filenames.BACKGROUND_IMAGES[3])).getImage();
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

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public void stopGame() {
        // TODO: Implement when the stop it's called
        State.setCurrentState(new GameOverState(handler));
    }
}

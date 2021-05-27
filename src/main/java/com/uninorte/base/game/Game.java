package com.uninorte.base.game;

import com.uninorte.base.api.GameClient;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.input.MouseManager;
import com.uninorte.base.game.states.*;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.game.input.KeyManager;
import com.uninorte.base.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    public static final String FILENAME_SETTINGS =  System.getProperty("settings", "SpaceInvaders-Uninorte");

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

    public State mainState;
    public State gameSate;
    public State gameOverState;
    public State settingsState;
    public State winScreenState;
    public State singlePlayerState;
    public State multiplayerState;
    public State roomsState;
    public State signUpState;

    private Image background;

    private Settings settings;

    private GameClient gameClient;

    public Game(String title, Dimension windowSize) {
        this.title = title;
        this.windowSize = windowSize;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();

        settings = new Settings(FILENAME_SETTINGS);
    }

    private void init() {
        display = new Display(title, windowSize);
        display.getFrame().addKeyListener(keyManager);
        display.getGameCanvas().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getGameCanvas().addMouseListener(mouseManager);
        display.getGameCanvas().addMouseMotionListener(mouseManager);
        display.getGameCanvas().addMouseWheelListener(mouseManager);
        display.getFrame().addMouseWheelListener(mouseManager);

        Assets.init();

        handler = new Handler(this);
        handler.setSettings(settings);
        mainState = new MainState(handler);
        gameSate = new GameState(handler);
        gameOverState = new GameOverState(handler);
        settingsState = new SettingsState(handler);
        winScreenState = new WinScreenState(handler);
        singlePlayerState = new SingleplayerState(handler);
        multiplayerState = new MultiplayerState(handler);
        signUpState = new SignUpState(handler);
        roomsState = new RoomsState(handler);

        setRequestHandlers();
        loadUserIfExists();

        State.setCurrentState(mainState);
    }

    private void setRequestHandlers() {
        gameClient = new GameClient("http://localhost:8080");
        handler.setGameClient(gameClient);
    }

    private void loadUserIfExists() {
        String userData = settings.getData(Settings.USER_DATA_FILENAME);

        if (userData == null)
            return;

        User user = User.createFromJson(userData);
        gameClient.setCurrentUser(user);
    }

    private void update() {
        // Set mouse and key listeners
        keyManager.update();

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

    }

    public synchronized void start() {
        // Exit if the game is already running
        if (running)
            return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        // Exit if the game is not running
        if(!running)
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

    public void changeTitle(String title) {
        display.getFrame().setTitle("Space Invaders - " + title);
    }

    public void gameOver() {
        State.setCurrentState(gameOverState);
    }

    public void close() {
        display.close();
        gameOver();
        System.exit(1);
    }

    public void stopGame() {
        display.close();
        stop();
    }

    public void changeBackground(String path) {
        background = new ImageIcon(ContentLoader.loadImage(path)).getImage();
    }
}

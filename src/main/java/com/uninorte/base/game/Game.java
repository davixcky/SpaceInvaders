package com.uninorte.base.game;

import com.uninorte.base.Filenames;
import com.uninorte.base.api.GameClient;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.ContentLoader;
import com.uninorte.base.game.input.KeyManager;
import com.uninorte.base.game.input.MouseManager;
import com.uninorte.base.game.states.*;
import com.uninorte.base.settings.Settings;
import com.uninorte.base.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game implements Runnable {

    public static final String FILENAME_SETTINGS = System.getProperty("settings", "SpaceInvaders-Uninorte");

    private String title;
    private Dimension windowSize;
    private int choice = 0;
    public int lastVolume = 50;
    private boolean running = false;
    private Thread gameThread;

    private BufferStrategy bs;
    private Graphics g;
    protected ArrayList<BufferedImage> playerAssetsOptions;
    public BufferedImage playerAssets;
    protected ArrayList<BufferedImage> bgAssetsOptions;
    public BufferedImage getBgAssets;

    private Display display;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Handler handler;

    public State mainState;
    public State gameSate;
    public State gameStateMultiplayer;
    public State gameOverState;
    public State settingsState;
    public State winScreenState;
    public State singlePlayerState;
    public State multiplayerState;
    public State roomsState;
    public State signUpState;
    public State waitingRoomState;

    private Image background;

    public  Sound sound;

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

        sound = new Sound(true);

        addSound();
        playBackground();

        Assets.init();

        handler = new Handler(this);
        handler.setSettings(settings);

        setRequestHandlers();
        loadUserIfExists();

        mainState = new MainState(handler);
        gameSate = new GameState(handler);
        gameStateMultiplayer = new GameStateMultiplayer(handler);
        gameOverState = new GameOverState(handler);
        settingsState = new SettingsState(handler);
        winScreenState = new WinScreenState(handler);
        singlePlayerState = new SingleplayerState(handler);
        multiplayerState = new MultiplayerState(handler);
        signUpState = new SignUpState(handler);
        roomsState = new RoomsState(handler);
        waitingRoomState = new WaitingRoomState(handler);

        playerAssetSelection(0);

        State.setCurrentState(mainState);
    }

    private void setRequestHandlers() {
        gameClient = new GameClient("https://immense-everglades-29171.herokuapp.com");
        handler.setGameClient(gameClient);
    }

    private void loadUserIfExists() {
        String userData = settings.getData(Settings.USER_DATA_FILENAME);

        if (userData == null)
            return;

        User user = User.createFromJson(userData);
        gameClient.setCurrentUser(user);
    }

    public void playerAssetSelection(int index){
        playerAssetsOptions = Assets.getPlayerAssets();
        playerAssets = playerAssetsOptions.get(index);
    }

    public  BufferedImage getPlayerAssets(){return playerAssets;}

    public void setBgSelection(int index){
        bgAssetsOptions = Assets.getBgAssets();
        getBgAssets = bgAssetsOptions.get(index);
    }

    public BufferedImage getBgAssets() { return getBgAssets; }

    private void addSound() {
        sound.add(Sound.BACKGROUND, Filenames.MUSIC[0]);
        sound.add(Sound.GAMEOVER, Filenames.MUSIC[1]);
        sound.add(Sound.SHOOTS, Filenames.MUSIC[2]);
        sound.add(Sound.ALIEN, Filenames.MUSIC[3]);
        sound.add(Sound.PLAYER, Filenames.MUSIC[4]);
    }

    private void playBackground() {
        try {
            sound.play(Sound.BACKGROUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playEffects(int num) {
        try {
            switch (num) {
                case 0:
                    sound.playEff(Sound.GAMEOVER);
                    break;
                case 1:
                    sound.playEff(Sound.SHOOTS);
                    break;
                case 2:
                    sound.playEff(Sound.PLAYER);
                    break;
                case 3:
                    sound.playEff(Sound.ALIEN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBackground(int status) {
        choice += status;

        if(choice >= Filenames.BACKGROUND_IMAGES.length)
            choice = 0;

        if (choice < 0)
            choice = Filenames.BACKGROUND_IMAGES.length - 1;

        changeBackground(Filenames.BACKGROUND_IMAGES[choice]);
    }

    public void setBackground(){
        choice++;
        if(choice == 12)
            choice = 0;

        changeBackground(Filenames.BACKGROUND_IMAGES[choice]);
    }

    public String getNextBackgroundImage() {
        String name;

        if (choice + 1 == 12)
            name = Filenames.BACKGROUND_IMAGES[0];
        else
            name = Filenames.BACKGROUND_IMAGES[choice + 1];

        return name;
    }

    public void setVolume(int num, float volume){
        try{
            switch (num){
                case 0:
                    sound.setVolume(Sound.BACKGROUND, volume);
                    lastVolume = (int) sound.getVolume(Sound.BACKGROUND);
                    break;
                case 1:
                    sound.setVolume(Sound.GAMEOVER, volume);
                    break;
                case 2:
                    sound.setVolume(Sound.SHOOTS, volume);
                    break;
                case 3:
                    sound.setVolume(Sound.ALIEN, volume);
                    break;
            }
        }catch(Exception e){ }
    }

    public void gameoverSoundChange(float volume){
        try {
            sound.setVolume(Sound.BACKGROUND,volume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMuted(int num){
        try {
            switch(num) {
                case 0:
                    sound.setMuted(Sound.BACKGROUND);
                    break;
                case 1:
                    sound.setMuted(Sound.SHOOTS);
                    sound.setMuted(Sound.GAMEOVER);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        g.drawImage(background, 0, 0, windowSize.width, windowSize.height, null);

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

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
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

    public void changeTitle(String title) {
        display.getFrame().setTitle("Space Invaders - " + title);
    }

    public void gameOver() {
        State.setCurrentState(gameOverState);
        try {
            lastVolume = (int) sound.getVolume(Sound.BACKGROUND);
            gameoverSoundChange(0);
            if(sound.isMuted(Sound.GAMEOVER))
                return;

            playEffects(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

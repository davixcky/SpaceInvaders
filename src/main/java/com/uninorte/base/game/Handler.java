package com.uninorte.base.game;

import com.uninorte.base.api.GameClient;
import com.uninorte.base.api.models.Error;
import com.uninorte.base.game.board.Board;
import com.uninorte.base.game.highscore.HighScoreManager;
import com.uninorte.base.game.input.KeyManager;
import com.uninorte.base.game.input.MouseManager;
import com.uninorte.base.settings.Settings;

import java.awt.*;

public class Handler {

    private Game game;
    private Board board;
    private Settings settings;

    private GameClient gameClient;
    private Error error;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Dimension boardDimensions() {
        return game.getWindowSize();
    }

    public float getWithDividedBy2() {
        return boardDimensions().width >> 1;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public HighScoreManager getHighScoreManager() {
        return board.getHighScoreManager();
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getLastErrorMessage() {
        return error == null ? null : error.getErrorMessage();
    }

    public void clearError() {
        error = null;
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }
}

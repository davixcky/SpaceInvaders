package com.uninorte.base.game;

import com.uninorte.base.game.board.Board;
import com.uninorte.base.game.highscore.HighScoreManager;
import com.uninorte.base.input.KeyManager;

import java.awt.*;

public class Handler {

    private Game game;
    private Board board;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return  game;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Dimension boardDimensions() {
        return game.getWindowSize();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public HighScoreManager getHighScoreManager() {
        return board.getHighScoreManager();
    }
}

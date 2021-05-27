package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.board.Board;

import java.awt.*;

public class GameState extends State {

    private Board board;

    public GameState(Handler handler) {
        super(handler);
        board = new Board(handler);
        handler.setBoard(board);
    }

    @Override
    protected void initComponents() {

    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Game");
        board.update();
    }

    @Override
    public void render(Graphics g) {
        board.render(g);
    }

    public void reset() {
        board = new Board(handler);
        handler.setBoard(board);
    }

    public void nextLevel() {
        board = new Board(handler);
        board.setupLevel();
        handler.setBoard(board);
    }
}

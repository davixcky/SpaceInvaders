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
    public void update() {
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
}

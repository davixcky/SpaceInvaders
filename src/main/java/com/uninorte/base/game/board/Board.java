package com.uninorte.base.game.board;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.EntityManager;
import com.uninorte.base.game.entities.creatures.Player;

import java.awt.*;

public class Board {

    private Handler handler;
    private EntityManager entityManager;
    private Player mainPlayer;

    public Board(Handler handler) {
        this.handler = handler;

        createPlayer();
        this.entityManager = new EntityManager(handler, mainPlayer);
    }

    private void createPlayer() {
        Dimension windowSize = handler.boardDimensions();
        int x = (int) (windowSize.width  * 0.5f);
        int y = (int) (windowSize.height * 0.85f); // 85% to the bottom

        mainPlayer = new Player(handler, x, y);
    }

    public void update() {
        entityManager.update();
    }

    public void render(Graphics g) {
        entityManager.render(g);
    }
}

package com.uninorte.base.game.board;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.EntityManager;
import com.uninorte.base.game.entities.creatures.AliensManager;
import com.uninorte.base.game.entities.creatures.Player;
import com.uninorte.base.game.highscore.HighScoreManager;
import com.uninorte.base.game.states.State;

import java.awt.*;

public class Board {

    private Handler handler;
    private EntityManager entityManager;
    private AliensManager aliensManager;
    private HighScoreManager highScoreManager;
    private Player mainPlayer;

    public Board(Handler handler) {
        this.handler = handler;

        this.highScoreManager = new HighScoreManager();

        createPlayer();
        this.entityManager = new EntityManager(handler, mainPlayer);
        this.aliensManager = new AliensManager(handler, 5, 6);

        this.entityManager.addAliensManager(aliensManager);
    }

    private void createPlayer() {
        Dimension windowSize = handler.boardDimensions();
        int x = (int) (windowSize.width  * 0.5f);
        int y = (int) (windowSize.height * 0.9f); // 85% to the bottom

        mainPlayer = new Player(handler, x, y);
        highScoreManager.registerPlayer(mainPlayer);
    }

    public void update() {
        entityManager.update();
    }

    public void playerWin() {
        State.setCurrentState(handler.getGame().winScreenState);
    }

    public void render(Graphics g) {
        entityManager.render(g);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public AliensManager getAliensManager() {
        return aliensManager;
    }

    public HighScoreManager getHighScoreManager() { return highScoreManager; }

    public Player getMainPlayer() { return mainPlayer; }
}

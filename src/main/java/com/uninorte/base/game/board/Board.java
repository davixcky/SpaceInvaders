package com.uninorte.base.game.board;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.EntityManager;
import com.uninorte.base.game.entities.creatures.AliensManager;
import com.uninorte.base.game.entities.creatures.Player;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.highscore.HighScoreManager;
import com.uninorte.base.game.states.SingleplayerState;
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

    public void playerWinLevel() {
        ((SingleplayerState) handler.getGame().singleplayerState).increaseLevel();
        State.setCurrentState(handler.getGame().singleplayerState);
    }

    public void setupLevel() {
        int level = ((SingleplayerState) handler.getGame().singleplayerState).getCurrentLevel();

        System.out.println(400 - 50L * level);
        mainPlayer.getProjectilesManager().changeTimeBetweenShots(400 - 20L * level);
        aliensManager.getProjectilesManager().changeTimeBetweenShots(400 - 50L * level);
    }

    public void render(Graphics g) {
        Text.drawString(g, "LEVEL " + ((SingleplayerState) handler.getGame().singleplayerState).getCurrentLevel(),
                handler.boardDimensions().width / 2,
                20,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SLKSCR, 20));

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

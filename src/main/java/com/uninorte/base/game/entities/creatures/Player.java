package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Explosion;
import com.uninorte.base.game.states.SingleplayerState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Creature {

    private final ProjectilesManager projectilesManager;
    private final Explosion explosionController;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, new Dimension(60, 60));

        creatureAssetsOptions = Assets.getPlayerAssets();
        creatureAsset = creatureAssetsOptions.get(0);

        projectilesManager = new ProjectilesManager(400);
        explosionController = new Explosion(Assets.ExplosionColor.RED);
    }

    @Override
    public void update() {
        if (isRenderingExplosion()) {
            explosionController.update();
            return;
        }

        getInput();
        move();
        shoot();

        projectilesManager.update();

        if (checkEntityCollisions(0, 0)) {
            hurt(1);
        }
    }

    @Override
    public void render(Graphics g) {
        if (explosionController.isRendering()) {
            if (explosionController.getIndex() % 2 ==  0)
                g.drawImage(creatureAsset, (int) x, (int) y, entityDimensions.width, entityDimensions.height, null);
        } else {
            g.drawImage(creatureAsset, (int) x, (int) y, entityDimensions.width, entityDimensions.height, null);
        }

        g.setColor(Color.white);
        g.drawString(Integer.toString(handler.getHighScoreManager().getPlayerPoints(this)), 30, 30);

        showLives(g);

        projectilesManager.render(g);
    }

    @Override
    public void die() {
        ((SingleplayerState) handler.getGame().singleplayerState).resetLevel();
        handler.getGame().gameOver();
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().getStatusKey(KeyEvent.VK_A))
            xMove = -speed;
        if (handler.getKeyManager().getStatusKey(KeyEvent.VK_D))
            xMove = speed;

    }

    @Override
    public void move() {
        moveX();
    }

    @Override
    public void moveX() {
        if (x + xMove + entityDimensions.width >= handler.boardDimensions().width) {
            x = handler.boardDimensions().width - entityDimensions.width;
        } else if (x + xMove <= 0) {
            x = 0;
        } else {
            x += xMove;
        }
    }

    @Override
    public void hurt(int amt) {
        super.hurt(amt);

        explosionController.reset();
        explosionController.startRender();
    }

    private void shoot() {
        projectilesManager.addProjectile(() -> (handler.getKeyManager().getStatusKey(KeyEvent.VK_SPACE) || true) && explosionController.isAvailableRender(),
                new Projectile(handler, x, y, Projectile.Direction.UP, this));
    }

    public boolean isRenderingExplosion() {
        return explosionController.isRendering();
    }

    public ProjectilesManager getProjectilesManager() {
        return projectilesManager;
    }

    private void showLives(Graphics g) {
        int x = (int) (handler.boardDimensions().width * 0.87f);
        int y = (int) (handler.boardDimensions().height * 0.02f);

        g.setColor(new Color(255, 255, 255, 60));
        g.fillRect(x - 10, y - 10, 100, 40);
        for (int i = 0; i < health; i++) {
            g.drawImage(creatureAsset, x, y, 20, 20, null);
            x += 30;
        }
    }
}

package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.Entity;
import com.uninorte.base.game.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Creature{

    enum Direction {
        DOWN,
        UP
    }

    private boolean active;
    private Direction direction;
    private Entity parent;

    private BufferedImage projectileAsset;

    public Projectile(Handler handler, float x, float y, Direction direction, Entity parent) {
        super(handler, x, y, new Dimension(70, 70));

        this.direction = direction;
        this.parent = parent;
        active = true;
        yMove = -speed;

        bounds.width = entityDimensions.width - 30;
        setProjectileAsset();
    }

    @Override
    public void update() {
        move();

        // Check if a projectile is impacting an alien
        Alien e = getAlienEntityCollision(0, 0);
        if (e != null) {
            // Check if the projectile was triggered by a Player
            if (parent instanceof Player) {
                e.hurt(DEFAULT_HEALTH);
                handler.getHighScoreManager().addPointsToPlayer((Player) parent, e.getPoints());
                active = false;
            }
        }

        // Check if the projectile is impacting a user
        if (parent instanceof Alien) {
            Player p = handler.getBoard().getMainPlayer();
            if (getCollisionBounds(0f, 0f).intersects(p.getCollisionBounds(0f, 0f)) && !p.isRenderingExplosion()) {
                p.hurt(1);
                active = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(projectileAsset, (int) x, (int) y, entityDimensions.width, entityDimensions.height, null);
    }

    @Override
    public void move() {
        moveY();
    }

    @Override
    public void moveY() {
        switch (direction) {
            case DOWN -> {
                y -= yMove;

                if (y >= handler.boardDimensions().height)
                    active = false;
            }
            case UP -> {
                y += yMove;

                if (y <= 0)
                    active = false;
            }
        }
    }

    @Override
    public void die() {
    }

    public boolean isActive() {
        return active;
    }

    private void setProjectileAsset() {
        if (parent instanceof Player)
            projectileAsset = Assets.getBulletAssets(1);
        else
            projectileAsset = Assets.getBulletAssets(0);

        switch (direction) {
            case DOWN -> projectileAsset = Assets.rotate(projectileAsset, 90);
            case UP -> projectileAsset = Assets.rotate(projectileAsset, -90);
        }
    }
}

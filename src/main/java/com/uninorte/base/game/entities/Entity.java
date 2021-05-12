package com.uninorte.base.game.entities;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.creatures.Alien;

import java.awt.*;
import java.awt.geom.Area;
import java.nio.channels.Pipe;
import java.util.ArrayList;

public abstract class Entity {

    public static final int DEFAULT_HEALTH = 3;

    protected Handler handler;
    protected Dimension entityDimensions;
    protected float x, y;
    protected boolean active;
    protected int health;
    protected Rectangle bounds;

    public Entity(Handler handler, float x, float y, Dimension entityDimensions) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.entityDimensions = entityDimensions;
        this.active = true;
        this.health = DEFAULT_HEALTH;

        bounds = new Rectangle(new Point(0, 0), entityDimensions);
    }

    public abstract void update();
    public abstract void render(Graphics g);
    public abstract void die();

    public void hurt(int amt) {
        health -= amt;
        if (health <= 0) {
            active = false;
            die();
        }
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e: handler.getBoard().getAliensManager().getAliens()) {
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }

        return false;
    }

    public Entity getAlienEntityCollision(float xOffset, float yOffset) {
        for (Entity e: handler.getBoard().getAliensManager().getAliens()) {
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return e;
            }
        }

        return null;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}

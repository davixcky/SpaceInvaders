package com.uninorte.base.game.entities;

import com.uninorte.base.game.Handler;

import java.awt.*;
import java.awt.geom.Area;

public abstract class Entity {

    public static final int DEFAULT_HEALTH = 3;

    protected Handler handler;
    protected float x, y;
    protected Dimension size;
    protected boolean active;
    protected int health;

    public Entity(Handler handler, float x, float y, Dimension size) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.size = size;
        this.active = true;
        this.health = DEFAULT_HEALTH;

        new Area(new Rectangle(12, 23, 4, 5));

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

}

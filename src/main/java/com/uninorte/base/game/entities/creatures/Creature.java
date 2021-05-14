package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 40,
            DEFAULT_CREATURE_HEIGHT = 40;

    protected float speed;
    protected float xMove, yMove;
    protected ArrayList<BufferedImage> creatureAssetsOptions;
    protected BufferedImage creatureAsset;

    protected int indexCreature;

    public Creature(Handler handler, float x, float y, Dimension size) {
        super(handler, x, y, size);
        speed = DEFAULT_SPEED;
        xMove = yMove = 0;
        indexCreature = 1;
    }

    public void move() {
        moveX();
        moveY();
    }

    public void moveX() {
        float width = (float) handler.boardDimensions().getWidth();

        if (xMove > 0) {
            x += xMove;

            if (x + entityDimensions.width >= width) x = width;
        } else if (xMove < 0) {
            x += xMove;

            if (x <= 0) x = 0;
        }
    }

    public void moveY(){
        float height = (float) handler.boardDimensions().getHeight();

        if (yMove > 0) {
            y += yMove;

            if (y + entityDimensions.height >= height) y = height;
        } else if (yMove < 0) {
            y += yMove;

            if (y <= 0) y = 0;
        }
    }
}

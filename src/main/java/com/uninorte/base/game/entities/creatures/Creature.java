package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.display.Display;
import com.uninorte.base.game.entities.Entity;

import java.awt.*;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 3.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 60,
            DEFAULT_CREATURE_HEIGHT = 60;

    protected float speed;

    public Creature(Handler handler, float x, float y, Dimension size) {
        super(handler, x, y, size);
        speed = DEFAULT_SPEED;
    }

    public void move(){
    }

    public void moveX(){
    }

    public void moveY(){
    }
}

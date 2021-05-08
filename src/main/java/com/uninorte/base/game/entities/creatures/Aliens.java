package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;

import java.awt.*;

public class Aliens extends Creature {
    public Aliens(Handler handler, float x, float y) {
        super(handler, x, y, new Dimension(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
    }

    @Override
    public void die() {

    }
}

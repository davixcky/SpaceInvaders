package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Creature {

    private int i = 0;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, new Dimension(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT));
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        ArrayList<BufferedImage> assets = Assets.getAliensAssets(Assets.AlienName.B, Assets.AlienColor.RED);;
        g.drawImage(assets.get(0), (int) x + DEFAULT_CREATURE_WIDTH + 40, (int) y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, null);
//        g.drawImage(assets.get(1), (int) x + DEFAULT_CREATURE_WIDTH, (int) y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, null);




    }

    @Override
    public void die() {

    }
}

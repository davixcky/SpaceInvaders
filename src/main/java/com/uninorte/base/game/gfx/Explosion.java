package com.uninorte.base.game.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Explosion {

    private boolean isAvailableRender, isRendering;
    private ArrayList<BufferedImage> explosionAssets;
    private int index;

    public Explosion(Assets.ExplosionColor color) {
        explosionAssets = Assets.getAliensExplosionsAssets(color);
        index = 0;
        isAvailableRender = true;
        isRendering = false;
    }

    public void startRender() {
        isAvailableRender = false;
        isRendering = true;
    }

    public void update() {
        if (isRendering) {
            index++;

            if (index >= explosionAssets.size()) {
                isRendering = false;
            }
        }
    }

    public boolean isRendering() {
        return isRendering;
    }

    public boolean isAvailableRender() {
        return isAvailableRender;
    }

    public BufferedImage getCurrentFrame() {
        return explosionAssets.get(index);
    }

    public boolean isHalfRendering() {
        return index <= explosionAssets.size();
    }
}

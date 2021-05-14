package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Explosion;

import java.awt.*;

public class Alien extends Creature {
    private final int totalCols;
    private final float initialX;
    private final int columnPosition;

    private Assets.AlienColor lastColor;
    private Assets.AlienName lastName;

    private final Explosion explosionController;

    public Alien(Handler handler, float x, float y, int columnPosition, int totalCols) {
        super(handler, x, y, new Dimension(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT));

        initialX = x;
        this.columnPosition = columnPosition;
        this.totalCols = totalCols;

        lastName = Assets.AlienName.A;
        lastColor = Assets.AlienColor.BLUE;
        setCurrentAsset();

        explosionController = new Explosion(Assets.ExplosionColor.PINK);

        xMove = speed;
    }

    public void setAlienAsset(Assets.AlienName name, Assets.AlienColor color) {
        lastColor = color;
        lastName = name;

        creatureAssetsOptions = Assets.getAliensAssets(name, color);
        creatureAsset = creatureAssetsOptions.get(0);
    }

    @Override
    public void update() {
        setDirection();
        move();
        explosionController.update();
    }

    @Override
    public void render(Graphics g) {
        if (active) {
            g.drawImage(creatureAsset, (int) x, (int) y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, null);
            return;
        }

        if (explosionController.isRendering()) {
            g.drawImage(explosionController.getCurrentFrame(), (int) x - DEFAULT_CREATURE_WIDTH / 2, (int) y - DEFAULT_CREATURE_HEIGHT / 2, 80, 80, null);
            if (explosionController.isHalfRendering())
                g.drawString(Integer.toString(getPoints()), (int) x, (int) y);
        }
    }

    @Override
    public void die() {
        explosionController.startRender();
    }

    private void setDirection() {
        yMove = 0;

        if (xMove < 0) {
            if (x == initialX) {
                yMove = 30;
                xMove = speed;
                changeColor();
            } else {
                xMove = -speed;
            }
        } else if (xMove > 0) {
            // width - (40 + 10) * nCols - 10
            int totalSpace = handler.boardDimensions().width - 50 * totalCols - 10;
            if (x >= totalSpace + ((columnPosition + 1) * 50)) {
                xMove = -speed;
                yMove = 30;
                changeColor();
            }

        }
    }

    @Override
    public void move() {
        if (xMove > 0) {
            x += xMove;
        } else if (xMove < 0) {
            x += xMove;
        }

        if (yMove != 0) {
            y += yMove;
        }
    }

    public Assets.AlienName getAlienName(int i) {
        return switch (i % 3) {
            case 1 -> Assets.AlienName.B;
            case 2 -> Assets.AlienName.C;
            default -> Assets.AlienName.A;
        };
    }

    public Assets.AlienColor getAlienColor(int j) {
        return switch (j % 4) {
            case 1 -> Assets.AlienColor.BLUE;
            case 2 -> Assets.AlienColor.WHITE;
            case 3 -> Assets.AlienColor.YELLOW;
            default -> Assets.AlienColor.RED;
        };
    }

    public void setAlienName(int i) {
        lastName = getAlienName(i);
        setCurrentAsset();
    }

    public void setAlienColor(int j) {
        lastColor = getAlienColor(j);
        setCurrentAsset();
    }

    private void setCurrentAsset() {
        creatureAssetsOptions = Assets.getAliensAssets(lastName, lastColor);
        creatureAsset = creatureAssetsOptions.get(0);
    }

    private void changeColor() {
        lastColor = getAlienColor(lastColor.ordinal() + 1);
        setCurrentAsset();
    }

    public int getPoints() {
        int points;

        switch (lastName) {
            case A -> points = 20;
            case B -> points = 30;
            case C -> points = 10;
            default -> points = 0;
        }

        return points;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRenderingExplosion() {
        return explosionController.isRendering() || explosionController.isAvailableRender();
    }
}

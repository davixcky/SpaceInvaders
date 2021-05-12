package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.Entity;

import java.awt.*;

public class Projectile extends Creature{

    enum Direction {
        DOWN,
        UP
    }

    private boolean active;
    private Direction direction;
    private Entity parent;
    private Color color;

    public Projectile(Handler handler, float x, float y, Direction direction, Entity parent, Color color) {
        super(handler, x, y, new Dimension(5, 10));

        this.direction = direction;
        this.parent = parent;
        this.color = color;
        active = true;
        yMove = -speed;
    }

    @Override
    public void update() {
        move();

        // Check if a projectile is impacting an alien
        Entity e = getAlienEntityCollision(0, yMove);
        if (e != null) {
            // Check if the projectile was triggered by a Player
            if (parent instanceof Player) {
                e.hurt(DEFAULT_HEALTH);
                active = false;
            }
        }

        // Check if the projectile is impacting a user
        if (parent instanceof Alien) {
            Player p = handler.getBoard().getMainPlayer();
            if (getCollisionBounds(0f, 0f).intersects(p.getCollisionBounds(0f, 0f))) {
                p.hurt(1);
                active = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect((int) x, (int) y, entityDimensions.width, entityDimensions.height);
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
        System.out.println(active);
    }

    public boolean isActive() {
        return active;
    }

}

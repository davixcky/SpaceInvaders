package com.uninorte.base.game.entities;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.creatures.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;

    public EntityManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;

        entities = new ArrayList<>();
        addEntity(player);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void update() {
        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()) {
            Entity e = it.next();
            e.update();
        }
    }

    public void render(Graphics g) {
        for (Entity e: entities) {
            e.render(g);
        }
    }

}

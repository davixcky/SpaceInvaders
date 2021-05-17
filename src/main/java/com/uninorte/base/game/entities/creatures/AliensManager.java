package com.uninorte.base.game.entities.creatures;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.states.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AliensManager {

    private ArrayList<Alien> aliens;
    private Handler handler;
    private int rows, cols;
    private Random random;

    private ProjectilesManager projectilesManager;

    public AliensManager(Handler handler, int rows, int cols) {
        this.handler = handler;
        this.rows = rows;
        this.cols = cols;
        aliens = new ArrayList<>();
        random = new Random();

        projectilesManager = new ProjectilesManager(1000);

        createAliens();
    }

    private void createAliens() {
        int x, y;
        int shipName = 1;
        int shipColor = 0;

        y = 10;
        for (int i = 0; i < rows; i++, y += Creature.DEFAULT_CREATURE_HEIGHT + 20) {
            for (int j = x = 0; j < cols; j++, x += Creature.DEFAULT_CREATURE_WIDTH + 10) {
                Alien a = new Alien(handler, x, y, j, cols);
                a.setAlienAsset(a.getAlienName(shipName), a.getAlienColor(shipColor));
                aliens.add(a);
            }

            if (i % 2 == 0) {
                shipName += 2;
                shipColor += 1;
            }
        }

    }

    public void update() {
        Iterator<Alien> it = aliens.iterator();
        while (it.hasNext()) {
            Alien a = it.next();
            a.update();

            if (!a.isActive() && !a.isRenderingExplosion())
                it.remove();
        }

        if (aliens.size() != 0) {
            createProjectile();
        }

        if (projectilesManager.activeProjectiles() )
            projectilesManager.update();
        else if (aliens.size() == 0 && !projectilesManager.activeProjectiles())
            handler.getBoard().playerWin();
    }

    public void render(Graphics g) {
        for (Alien a: aliens) {
            a.render(g);
        }

        projectilesManager.render(g);
    }

    public ArrayList<Alien> getAliens() {
        return aliens;
    }

    public void createProjectile() {
        int currentIndex = random.nextInt((aliens.size()));
        Alien a = aliens.get(currentIndex);

        projectilesManager.addProjectile(() -> true, new Projectile(handler, a.getX(), a.getY(), Projectile.Direction.DOWN, a));
    }

}

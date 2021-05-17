package com.uninorte.base.game.entities.creatures;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ProjectilesManager {
    private long lastTimeShoot;
    private final long timeBetweenShots;
    private long shootTimer;

    private ArrayList<Projectile> projectiles;

    private boolean isFirstShoot = true;

    public ProjectilesManager(long timeBetweenShots) {
        projectiles = new ArrayList<>();

        this.timeBetweenShots = timeBetweenShots;
        shootTimer = this.timeBetweenShots;
    }

    public void update() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile s = it.next();
            s.update();

            if (!s.isActive()) {
                it.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Projectile shoot : projectiles) {
            shoot.render(g);
        }
    }

    public void addProjectile(ProjectileHandler handler, Projectile projectile) {
        shootTimer += System.currentTimeMillis() - lastTimeShoot;
        lastTimeShoot = System.currentTimeMillis();

        if (isFirstShoot)
            shootTimer = 0; isFirstShoot = false;

        if (shootTimer < timeBetweenShots )
            return;

        if (handler.isValidToAdd()) {
            shootTimer = 0;
            projectiles.add(projectile);
        }
    }

    public boolean activeProjectiles() {
        return projectiles.size() > 0;
    }

    public interface ProjectileHandler {
        boolean isValidToAdd();
    }
}

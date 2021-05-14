package com.uninorte.base.game.gfx;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Assets {

    public enum AlienColor {
        RED,
        YELLOW,
        BLUE,
        WHITE
    }

    public enum AlienName {
        A,
        B,
        C
    }

    public enum ExplosionColor {
        GREEN,
        BLUE,
        CYAN,
        ORANGE,
        PINK,
        PURPLE,
        RED,
        YELLOW
    }

    private static ArrayList<BufferedImage> playerAssets;
    private static ArrayList<BufferedImage> aliensAssets;
    private static ArrayList<BufferedImage> bulletAssets;
    private static HashMap<String, ArrayList<BufferedImage>> explosions;

    public static void init() {
        playerAssets = loadSprites(60, 60, 3, "/textures/ships.png");
        aliensAssets = loadSprites(40, 40, 24, "/textures/aliens.png");
        bulletAssets = loadSprites(120, 120, 2, "/textures/bullets.png");

        explosions = new HashMap<>();
        explosions.put(getColorString(ExplosionColor.GREEN), loadSprites(100, 100, 56, "/textures/explosions/green.png"));
        explosions.put(getColorString(ExplosionColor.BLUE), loadSprites(100, 100, 56, "/textures/explosions/blue.png"));
        explosions.put(getColorString(ExplosionColor.CYAN), loadSprites(100, 100, 56, "/textures/explosions/cyan.png"));
        explosions.put(getColorString(ExplosionColor.ORANGE), loadSprites(100, 100, 56, "/textures/explosions/orange.png"));
        explosions.put(getColorString(ExplosionColor.PINK), loadSprites(100, 100, 56, "/textures/explosions/pink.png"));
        explosions.put(getColorString(ExplosionColor.PURPLE), loadSprites(100, 100, 56, "/textures/explosions/purple.png"));
        explosions.put(getColorString(ExplosionColor.RED), loadSprites(100, 100, 56, "/textures/explosions/red.png"));
        explosions.put(getColorString(ExplosionColor.YELLOW), loadSprites(100, 100, 56, "/textures/explosions/yellow.png"));
    }

    public static ArrayList<BufferedImage> getPlayerAssets() {
        return playerAssets;
    }

    public static ArrayList<BufferedImage> getAliensAssets(AlienName name, AlienColor color) {
        ArrayList<BufferedImage> assets = new ArrayList<>(2);

        int pos = name.ordinal() * 8 + color.ordinal() * 2;

        assets.add(aliensAssets.get(pos));
        assets.add(aliensAssets.get(pos + 1));

        return assets;
    }

    public static ArrayList<BufferedImage> getAliensExplosionsAssets(ExplosionColor color) {
        return explosions.get(getColorString(color));
    }

    public static BufferedImage getBulletAssets(int i) {
        return bulletAssets.get(i);
    }

    public static BufferedImage rotate(BufferedImage bimg, double angle) {

        int w = bimg.getWidth();
        int h = bimg.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawImage(bimg, null, 0, 0);
        graphic.dispose();
        return rotated;
    }

    private static ArrayList<BufferedImage> loadSprites(int width, int height, int total, String filename) {
        SpriteSheet sheet = new SpriteSheet(ContentLoader.loadImage(filename));
        int count = 0;

        ArrayList<BufferedImage> assets = new ArrayList<>();

        for (int j = 0; j < sheet.getHeight() && count < total; j += height) {
            for (int i = 0; i < sheet.getWidth() && count < total; i += width) {
                assets.add(sheet.crop(i, j, width, height));
                count++;
            }
        }

        return assets;
    }

    private static String getColorString(ExplosionColor color) {
        String colorStr = "";
        
        switch (color) {
            case GREEN -> colorStr =  "green";
            case RED -> colorStr = "red";
            case YELLOW -> colorStr = "yellow";
            case PINK -> colorStr = "pink";
            case PURPLE -> colorStr = "purple";
            case ORANGE -> colorStr = "orange";
            case BLUE -> colorStr = "blue";
            case CYAN -> colorStr = "cyan";
        }

        return colorStr;
    }

}

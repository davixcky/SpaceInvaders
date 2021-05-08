package com.uninorte.base.game.gfx;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    private static ArrayList<BufferedImage> playerAssets;
    private static ArrayList<BufferedImage> aliensAssets;

    public static void init() {
        playerAssets = loadSprites(60, 60, 3, "/textures/ships.png");
        aliensAssets = loadSprites(40, 40, 24, "/textures/aliens.png");
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

    private static ArrayList<BufferedImage> loadSprites(int width, int height, int total, String filename) {
        SpriteSheet sheet = new SpriteSheet(ContentLoader.loadImage(filename));
        int count = 0;

        ArrayList<BufferedImage> assets = new ArrayList<>();

        for (int j = 0; j < sheet.getHeight() && count < total; j+= height) {
            for (int i = 0; i < sheet.getWidth() && count < total; i+= width) {
                assets.add(sheet.crop(i, j, width, height));
                count++;
            }
        }

        return assets;
    }


}

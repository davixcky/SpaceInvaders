package com.uninorte.base.game.gfx;

import java.awt.*;
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

    public enum Fonts {
        SLKSCR_100,
        SLKSCR_10,
    }

    public enum FontsName {
        SLKSCR,
        SPACE_MISSION,
        SPORT_TYPO,
        JOYSTIX
    }

    public enum UI_ELEMENTS {
        BUTTONS,
        SLIDER,
        BUTTONS_NON_SQUARE
    }

    private static ArrayList<BufferedImage> playerAssets;
    private static ArrayList<BufferedImage> aliensAssets;
    private static ArrayList<BufferedImage> bulletAssets;
    private static HashMap<String, ArrayList<BufferedImage>> explosions;

    private static HashMap<String, ArrayList<BufferedImage>> uiComponents;

    private static HashMap<String, Font> fonts;

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

        uiComponents = new HashMap<>();
        uiComponents.put(getUiString(UI_ELEMENTS.BUTTONS), loadSprites(105, 21, 4, "/ui/labels.png"));
        uiComponents.put(getUiString(UI_ELEMENTS.BUTTONS_NON_SQUARE), loadSprites(134, 119, 2, "/ui/buttons-hexagons.png"));

        SpriteSheet slidersSheet = new SpriteSheet(ContentLoader.loadImage("/ui/sheet-slider.png"));
        ArrayList<BufferedImage> sliders = new ArrayList<>();
        sliders.add(slidersSheet.crop(0, 0, 245, 18));
        sliders.add(slidersSheet.crop(245, 0, 22, 17));
        uiComponents.put(getUiString(UI_ELEMENTS.SLIDER), sliders);

        fonts = new HashMap<>();
        fonts.put(getFontString(Fonts.SLKSCR_100), ContentLoader.loadFont("/fonts/slkscr.ttf", 100));
        fonts.put(getFontString(Fonts.SLKSCR_10), ContentLoader.loadFont("/fonts/slkscr.ttf", 10));
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

    public static Font getFont(Fonts font) {
        return fonts.get(getFontString(font));
    }

    public static Font getFont(FontsName fontName, int size) {
        return ContentLoader.loadFont(getFontName(fontName), size);
    }

    public static ArrayList<BufferedImage> getUiComponents(UI_ELEMENTS uiElement) {
        return uiComponents.get(getUiString(uiElement));
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
        int w = image.getWidth();
        int h = image.getHeight();

        BufferedImage rotated = new BufferedImage(w, h, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.rotate(Math.toRadians(angle), w >> 1, h >> 1);
        graphic.drawImage(image, null, 0, 0);
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

    private static String getFontString(Fonts font) {
        String fontStr = "";

        switch (font) {
            case SLKSCR_100 -> fontStr =  "slkscr100";
        }

        return fontStr;
    }

    private static String getFontName(FontsName name) {
        String fontStr = "";

        switch (name) {
            case SLKSCR -> fontStr = "/fonts/slkscr.ttf";
            case SPACE_MISSION -> fontStr = "/fonts/space-mission.otf";
            case SPORT_TYPO -> fontStr = "/fonts/sport-typo.ttf";
            case JOYSTIX -> fontStr = "/fonts/joystix_monospace.ttf";
        }

        return fontStr;
    }

    private static String getUiString(UI_ELEMENTS uiElement) {
        String uiElementStr = "";

        switch (uiElement) {
            case BUTTONS -> uiElementStr =  "buttons";
            case SLIDER -> uiElementStr = "slider";
            case BUTTONS_NON_SQUARE -> uiElementStr = "buttonsNonSquare";
        }

        return uiElementStr;
    }

}

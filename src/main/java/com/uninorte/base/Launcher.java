package com.uninorte.base;

import com.uninorte.base.game.Game;

import java.awt.Dimension;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");

        Game game = new Game("Game", new Dimension(1080, 720));
        game.changeBackground(Filenames.BACKGROUND_IMAGES[3]);
        game.start();
    }

}

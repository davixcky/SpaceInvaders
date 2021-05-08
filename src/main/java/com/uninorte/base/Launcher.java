package com.uninorte.base;

import com.uninorte.base.game.Game;

import java.awt.Dimension;

public class Launcher {
    public static void main(String[] args) {
//        Window mainWindow = new Window("Testing console", new Dimension(800, 600));
//
        Game game = new Game("Testing", new Dimension(1080, 720));
        game.start();

    }

}

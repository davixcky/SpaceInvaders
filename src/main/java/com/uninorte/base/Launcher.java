package com.uninorte.base;

import com.uninorte.base.display.Window;

import java.awt.Dimension;

public class Launcher {
    public static void main(String[] args) {
        Window mainWindow = new Window("Testing console", new Dimension(800, 600));
    }
}

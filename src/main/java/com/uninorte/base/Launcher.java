package com.uninorte.base;

import com.uninorte.base.display.Window;

import java.awt.Dimension;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        new Window("Testing console", new Dimension(1080, 720));
    }

}

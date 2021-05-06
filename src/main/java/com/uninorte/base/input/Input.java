package com.uninorte.base.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("hi");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("hi 2");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("hi 3");
    }
}

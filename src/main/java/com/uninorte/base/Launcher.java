package com.uninorte.base;

import com.uninorte.base.display.Window;
import com.uninorte.base.menus.Principal;
import com.uninorte.base.menus.Settings;

import java.awt.Dimension;
import java.net.URISyntaxException;

public class Launcher {
    public static void main(String[] args) throws URISyntaxException {
        Window mainWindow = new Window("Testing console", new Dimension(800, 600));
        Principal principal = new Principal(mainWindow);
    }
}

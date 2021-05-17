package com.uninorte.base.game.modes.multiplayer;

import javafx.scene.web.WebEngine;

public class JSBridge {

    WebEngine webEngine;

    JSBridge(WebEngine webEngine) {
        this.webEngine = webEngine;
    }

    public boolean checkSolution(String solve) {
        System.out.println("Input: " + solve);
        if (solve.equalsIgnoreCase("god")) {
            webEngine.executeScript("document.getElementById(\"out\").classList.add(\"enabled\");");
            return true;
        } else return false;
    }

}

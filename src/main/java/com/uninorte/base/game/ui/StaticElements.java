package com.uninorte.base.game.ui;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.states.GameState;
import com.uninorte.base.game.states.SingleplayerState;
import com.uninorte.base.game.states.State;

import java.awt.*;

public class StaticElements {

    public static UIButton settingsBtn(State parent, Handler handler, float x, float y) {
        UIButton settingsBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> State.setCurrentState(handler.getGame().settingsState));
        settingsBtn.setText("SETTINGS");
        settingsBtn.setHover(UIButton.btnHoverImager, "SETTINGS");

        return settingsBtn;
    }

    public static UIButton exitBtn(State parent, Handler handler, float x, float y) {
        UIButton exitBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> handler.getGame().close());
        exitBtn.setText("EXIT");
        exitBtn.setHover(UIButton.btnHoverImager, "CLOSE GAME");

        return exitBtn;
    }

    public static UIButton multiplayerBtn(State parent, Handler handler, float x, float y) {
        UIButton multiplayerBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> State.setCurrentState(handler.getGame().multiplayerState));
        multiplayerBtn.setText("MULTIPLAYER");
        multiplayerBtn.setHover(UIButton.btnHoverImager, "ONLINE");

        return multiplayerBtn;
    }

    public static UIButton singleplayerBtnn(State parent, Handler handler, float x, float y) {
        UIButton multiplayerBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> State.setCurrentState(handler.getGame().singleplayerState));
        multiplayerBtn.setText("SINGLEPLAYER");
        multiplayerBtn.setHover(UIButton.btnHoverImager, "OFFLINE");

        return multiplayerBtn;
    }

    public static UIButton newGameBtn(State parent, Handler handler, float x, float y) {
        UIButton newGameBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> {
            ((GameState) handler.getGame().gameSate).reset();
            ((SingleplayerState) handler.getGame().singleplayerState).resetLevel();
            State.setCurrentState(handler.getGame().gameSate);
        });
        newGameBtn.setText("NEW GAME");
        newGameBtn.setHover(UIButton.btnHoverImager, "START GAME");
        newGameBtn.setSize(new Dimension(105, 40));

        return newGameBtn;
    }

    public static UIButton menuBtn(State parent, Handler handler, float x, float y) {
        UIButton menuBtn = new UIButton(parent, x, y, UIButton.btnImage, () -> State.setCurrentState(handler.getGame().mainState));
        menuBtn.setText("MENU");
        menuBtn.setHover(UIButton.btnHoverImager, "GO TO MAIN");

        return menuBtn;
    }


}

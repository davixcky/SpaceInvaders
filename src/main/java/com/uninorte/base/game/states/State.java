package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.ui.UIManager;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected Handler handler;

    protected UIManager uiManager;

    public State(Handler handler) {
        this.handler = handler;

        uiManager = new UIManager(handler);
    }

    public static void setCurrentState(State state) {
        currentState = state;
        state.setUiManager();
    }

    protected void setUiManager() {
        handler.getGame().getMouseManager().setUIManager(uiManager);
    }

    public static State getCurrentState() {
        return currentState;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}

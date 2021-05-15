package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public static void setCurrentState(State state) {
        currentState = state;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public abstract void update();
    public abstract void render(Graphics g);
}

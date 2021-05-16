package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.ui.UISlider;

import java.awt.*;

public class SettingsState extends State {

    public SettingsState(Handler handler) {
        super(handler);

        initComponents();
    }

    private void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = (int) (handler.boardDimensions().height / 2 + 101);

        UISlider uiSlider = new UISlider(this,  x + 30, y / 2, 30, 40, (System.out::println));
        uiSlider.setValue(20);

        uiManager.addObjects(uiSlider);
    }

    @Override
    public void update() {
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }

}

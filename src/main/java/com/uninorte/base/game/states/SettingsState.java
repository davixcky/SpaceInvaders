package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;
import com.uninorte.base.game.ui.UISlider;

import java.awt.*;

public class SettingsState extends State {

    public SettingsState(Handler handler) {
        super(handler);
    }

    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = (int) (handler.boardDimensions().height / 2 + 101);

        UISlider uiSlider = new UISlider(this,  x + 30, y / 2, 30, 40, (System.out::println));
        uiSlider.setValue(20);

        UIButton exitBtn = StaticElements.menuBtn(this, handler, 0, 0);

        uiManager.addObjects(uiSlider, exitBtn);
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Settings");
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }

}

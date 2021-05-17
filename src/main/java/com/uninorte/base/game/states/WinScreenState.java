package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;

import java.awt.*;

public class WinScreenState extends State {

    public WinScreenState(Handler handler) {
        super(handler);

        initComponents();
    }

    private void initComponents() {
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Player win");
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "PLAYER WIN",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.Fonts.SLKSCR_100));

        uiManager.render(g);
    }
}

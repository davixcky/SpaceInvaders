package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;

public class WinScreenState extends State {

    public WinScreenState(Handler handler) {
        super(handler);
    }

    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        UIButton newGameBtn = StaticElements.newGameBtn(this, handler, x, y);
        UIButton menuBtn = StaticElements.menuBtn(this, handler, x, UIButton.getHeightRelative(newGameBtn));
        UIButton settingsBtn = StaticElements.settingsBtn(this, handler, x, UIButton.getHeightRelative(menuBtn));
        UIButton exitBtn = StaticElements.exitBtn(this, handler, x, UIButton.getHeightRelative(settingsBtn));

        uiManager.addObjects(newGameBtn, menuBtn, settingsBtn, exitBtn);
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
                Assets.getFont(Assets.FontsName.SPORT_TYPO, 100));

        uiManager.render(g);
    }
}

package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;

public class GameOverState extends State {

    private int index = 0;
    private boolean increase = true;

    public GameOverState(Handler handler) {
        super(handler);
    }

    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        UIButton newGameBtn = StaticElements.newGameBtn(this, handler, x, y);
        UIButton menuBtn = StaticElements.menuBtn(this, handler, x, UIButton.getHeightRelative(newGameBtn));
        UIButton exitBtn = StaticElements.exitBtn(this, handler, x, UIButton.getHeightRelative(menuBtn));

        uiManager.addObjects(newGameBtn, menuBtn, exitBtn);
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Game Over");
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        Color textColor = new Color(255, 0, 0, index);
        index += increase ? 5 : -5;

        if (index == 255)
            increase = false;
        else if (index == 0)
            increase = true;

        Text.drawString(g, "GAME OVER",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                textColor,
                Assets.getFont(Assets.FontsName.SPACE_MISSION, 100));

        uiManager.render(g);
    }
}

package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.UIImageButton;
import com.uninorte.base.game.ui.UIManager;

import java.awt.*;

public class GameOverState extends State {

    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);

        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);


        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = (int) (handler.boardDimensions().height / 2 + 101);
        uiManager.addObject(new UIImageButton(this, x, y, 128, 64, Assets.btn_start, () -> {
            ((GameState) handler.getGame().gameSate).reset();
            State.setCurrentState(handler.getGame().gameSate);
        }));
    }

    @Override
    public void update() {
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "GAME OVER",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.Fonts.SLKSCR_100));

        uiManager.render(g);
    }
}

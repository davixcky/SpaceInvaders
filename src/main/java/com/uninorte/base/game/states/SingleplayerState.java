package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;

public class SingleplayerState extends State {

    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 6;

    private int level = MIN_LEVEL;

    public SingleplayerState(Handler handler) {
        super(handler);
    }

    protected void initComponents() {
        if (getCurrentLevel() == MAX_LEVEL)
            State.setCurrentState(handler.getGame().winScreenState);


        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        UIButton gameBtn;
        if (getCurrentLevel() == 1) {
            gameBtn = StaticElements.newGameBtn(this, handler, x, y);
        } else {
            gameBtn = new UIButton(this, x, y, UIButton.btnImage, () -> {
                ((GameState) handler.getGame().gameSate).nextLevel();
                State.setCurrentState(handler.getGame().gameSate);
            });

            gameBtn.setText("NEXT LEVEL");
            gameBtn.setHover(UIButton.btnHoverImager, "LEVEL " + getCurrentLevel());
            gameBtn.setSize(new Dimension(105, 40));
        }

        UIButton menuBtn = StaticElements.menuBtn(this, handler, x, UIButton.getHeightRelative(gameBtn));
        UIButton settingsBtn = StaticElements.settingsBtn(this, handler, x, UIButton.getHeightRelative(menuBtn));
        UIButton exitBtn = StaticElements.exitBtn(this, handler, x, UIButton.getHeightRelative(settingsBtn));

        uiManager.addObjects(gameBtn, menuBtn, settingsBtn, exitBtn);
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Multiplayer");
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {

        Text.drawString(g, "LEVEL " + getCurrentLevel(),
                handler.boardDimensions().width / 2,
                20,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SLKSCR, 20));

        Text.drawString(g, "SINGLEPLAYER",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SPORT_TYPO, 80));

        uiManager.render(g);
    }

    public void increaseLevel() {
        level += 1;
    }

    public int getCurrentLevel() {
        return level;
    }

    public void resetLevel() {
        level = MIN_LEVEL;
    }
}

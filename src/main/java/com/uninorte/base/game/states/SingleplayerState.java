package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SingleplayerState extends State {

    public SingleplayerState(Handler handler) {
        super(handler);
    }

    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        ArrayList<BufferedImage> buttonsAssets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS);
        BufferedImage btnImage = buttonsAssets.get(0);
        BufferedImage btnHoverImager = buttonsAssets.get(3);

        UIButton signIn = new UIButton(this, x, y, btnImage, () -> {
            ((GameState) handler.getGame().gameSate).reset();
            State.setCurrentState(handler.getGame().gameSate);
        });
        signIn.setText("NEW GAME");
        signIn.setHover(btnHoverImager, "START GAME");
        signIn.setSize(new Dimension(105, 40));

        UIButton menuBtn = new UIButton(this, x, signIn.getY() + signIn.getHeight() + 10, btnImage, () -> handler.getGame().stopGame());
        menuBtn.setText("MENU");
        menuBtn.setHover(btnHoverImager, "MENU");

        UIButton exitBtn = new UIButton(this, x, menuBtn.getY() + menuBtn.getHeight() + 10, btnImage, () -> handler.getGame().close());
        exitBtn.setText("EXIT");
        exitBtn.setHover(btnHoverImager, "EXIT");

        uiManager.addObjects(signIn, menuBtn, exitBtn);
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Multiplayer");
        uiManager.update();
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "SINGLEPLAYER",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SPORT_TYPO, 80));

        uiManager.render(g);
    }
}

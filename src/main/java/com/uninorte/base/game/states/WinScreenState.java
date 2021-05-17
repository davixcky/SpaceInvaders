package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class WinScreenState extends State {

    public WinScreenState(Handler handler) {
        super(handler);

        initComponents();
    }

    private void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        ArrayList<BufferedImage> buttonsAssets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS);
        BufferedImage btnImage = buttonsAssets.get(0);
        BufferedImage btnHoverImager = buttonsAssets.get(3);

        UIButton newGameBtn = new UIButton(this, x, y, btnImage, () -> {
            ((GameState) handler.getGame().gameSate).reset();
            State.setCurrentState(handler.getGame().gameSate);
        });
        newGameBtn.setText("NEW GAME");
        newGameBtn.setHover(btnHoverImager, "NEW GAME");
        newGameBtn.setSize(new Dimension(105, 40));

        UIButton menuBtn = new UIButton(this, x, newGameBtn.getY() + newGameBtn.getHeight() + 10, btnImage, () -> handler.getGame().stopGame());
        menuBtn.setText("MENU");
        menuBtn.setHover(btnHoverImager, "MENU");

        UIButton settingsBtn = new UIButton(this, x, menuBtn.getY() + menuBtn.getHeight() + 10, btnImage, () -> State.setCurrentState(handler.getGame().settingsState));
        settingsBtn.setText("SETTINGS");
        settingsBtn.setHover(btnHoverImager, "SETTINGS");

        UIButton exitBtn = new UIButton(this, x, settingsBtn.getY() + settingsBtn.getHeight() + 10, btnImage, () -> handler.getGame().close());
        exitBtn.setText("EXIT");
        exitBtn.setHover(btnHoverImager, "EXIT");

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

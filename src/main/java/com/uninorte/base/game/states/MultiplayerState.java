package com.uninorte.base.game.states;

import com.uninorte.base.api.models.User;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.states.multiplayer.MultiplayerController;
import com.uninorte.base.game.ui.StaticElements;
import com.uninorte.base.game.ui.UIButton;
import com.uninorte.base.settings.Settings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MultiplayerState extends State {

    private UIButton firstBtn;

    private User currentUser;

    private int opacity = 0;
    private boolean increase = true;

    public MultiplayerState(Handler handler) {
        super(handler);

        handler.setMultiplayerController(new MultiplayerController(handler));
        handler.getGameClient().registerController(handler.getMultiplayerController());
    }

    protected void initComponents() {
        int x = (int) (handler.boardDimensions().width * 0.5f - 128 / 2);
        int y = handler.boardDimensions().height / 2 + 101;

        ArrayList<BufferedImage> buttonsAssets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS);
        BufferedImage btnImage = buttonsAssets.get(0);
        BufferedImage btnHoverImager = buttonsAssets.get(3);

        currentUser = null;
        if (!handler.getSettings().fileExists(Settings.USER_DATA_FILENAME)) {
            firstBtn = new UIButton(this, x, y, btnImage, () -> {
                State.setCurrentState(handler.getGame().signUpState);
            });
            firstBtn.setText("SIGN UP");
            firstBtn.setHover(btnHoverImager, "SIGN UP");
            firstBtn.setSize(new Dimension(105, 40));

        } else {
            firstBtn = new UIButton(this, x, y, btnImage, () -> {
                State.setCurrentState(handler.getGame().roomsState);
            });
            firstBtn.setText("ROOMS");
            firstBtn.setHover(btnHoverImager, "ROOMS");

            currentUser = handler.getGameClient().getCurrentUser();
        }

        firstBtn.setSize(new Dimension(105, 40));


        UIButton menuBtn = StaticElements.menuBtn(this, handler, x, UIButton.getHeightRelative(firstBtn));
        UIButton exitBtn = StaticElements.exitBtn(this, handler, x, UIButton.getHeightRelative(menuBtn));

        uiManager.addObjects(firstBtn, menuBtn, exitBtn);
    }

    @Override
    public void update() {
        handler.getGame().changeTitle("Multiplayer");
        uiManager.update();

        opacity += increase ? 5 : -5;
        if (opacity >= 255)
            increase = false;
        else if (opacity <= 0)
            increase = true;
    }

    @Override
    public void render(Graphics g) {
        Text.drawString(g, "MULTIPLAYER",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SPORT_TYPO, 80));

        if (currentUser != null) {
            Color previousColor = g.getColor();
            g.setColor(new Color(255, 255, 200, 70));
            g.fillRect((int) (handler.boardDimensions().width * 0.74f), 6, 135, 100);

            currentUser.render(g, handler.boardDimensions().width * 0.8f, 20, true);

            Text.drawString(g, currentUser.getNickname(),
                    (int) (handler.boardDimensions().width * 0.8f),
                    20,
                    true,
                    new Color(255, 255, 255, opacity),
                    Assets.getFont(Assets.FontsName.SLKSCR, 20));

            g.setColor(previousColor);
        }

        String errorMessage  = handler.getLastErrorMessage();
        if (errorMessage != null) {
            Text.drawString(g, errorMessage,
                    handler.boardDimensions().width / 2,
                    (int) (handler.boardDimensions().height * 0.30f),
                    true,
                    Color.red,
                    Assets.getFont(Assets.FontsName.SLKSCR, 20));
        }


        uiManager.render(g);
    }

    @Override
    protected void stop() {
        super.stop();
    }
}

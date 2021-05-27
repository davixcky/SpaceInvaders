package com.uninorte.base.game.states;

import com.uninorte.base.api.GameClient;
import com.uninorte.base.api.models.Error;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.UIButton;
import com.uninorte.base.game.ui.UIInput;
import com.uninorte.base.settings.Settings;

import java.awt.*;

public class SignUpState extends State {

    private Error error;
    private UIInput nicknameInput;

    public SignUpState(Handler handler) {
        super(handler);
    }

    @Override
    protected void initComponents() {
        int x = (int) handler.getWithDividedBy2();
        int y = handler.boardDimensions().height / 2 + 101;

        nicknameInput = new UIInput(this, x, y + 40);
        nicknameInput.setListener(() -> {
            GameClient gameClient = handler.getGameClient();
            gameClient.createUser(nicknameInput.getText());

            error = gameClient.getLastError();
            if (error != null) {
                return;
            }

            try {
                handler.getSettings().saveData(Settings.USER_DATA_FILENAME, gameClient.getCurrentUser().toJson());
            } catch (Exception e) {
                handler.setError(new Error(e.getMessage()));
            }

            setCurrentState(handler.getGame().multiplayerState);
        });

        nicknameInput.setX(handler.getWithDividedBy2() - (nicknameInput.getWidth() >> 1));

        UIButton backBtn = new UIButton(this, 20, handler.boardDimensions().height - 30, UIButton.btnImage,
                () -> State.setCurrentState(handler.getGame().multiplayerState));
        backBtn.setText("BACK");
        backBtn.setHover(UIButton.btnHoverImager, "GO TO PREVIOUS");

        uiManager.addObjects(nicknameInput, backBtn);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Dimension text = Text.drawString(g, "SIGN UP",
                handler.boardDimensions().width / 2,
                handler.boardDimensions().height / 2,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.SPORT_TYPO, 80));

        if (error != null) {
            Text.drawString(g, error.getErrorMessage(),
                    handler.boardDimensions().width / 2,
                    handler.boardDimensions().height / 2 + text.height + 20,
                    true,
                    Color.red,
                    Assets.getFont(Assets.FontsName.SLKSCR, 20));
        }

        uiManager.render(g);
    }
}

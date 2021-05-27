package com.uninorte.base.game.states;

import com.uninorte.base.api.models.Error;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;
import com.uninorte.base.game.ui.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class RoomsState extends State {

    private Error error;

    int width = handler.boardDimensions().width;
    int height = handler.boardDimensions().height;

    private final Point cordTextError = new Point((int) (width * 0.5f), (int) (height * 0.2f));
    private final Point cordText1 = new Point((int) (width * 0.5f), (int) (height * 0.5f));
    private final Point cordJoinInput = new Point((int) (width * 0.5f - UIButton.btnImage.getWidth() * 0.5f), cordText1.y + 30);
    private final Point cordText2 = new Point((int) (width * 0.5f), cordJoinInput.y + UIButton.btnImage.getHeight() + 80);
    private final Point cordCreateBtn = new Point((int) (width * 0.5f), cordText2.y + 30);

    private final ArrayList<BufferedImage> assets;

    public RoomsState(Handler handler) {
        super(handler);

        assets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS_NON_SQUARE);
        cordCreateBtn.x -= assets.get(0).getWidth() * 0.5f;
    }

    @Override
    protected void initComponents() {

        UIInput joinCodeInput = new UIInput(this, cordJoinInput.x, cordJoinInput.y);
        joinCodeInput.setListener(() -> {
            handler.getGameClient().joinToRoom(joinCodeInput.getText().toUpperCase());
            joinRoom();
        });

        UIButton newRoomBtn = new UIButton(this, cordCreateBtn.x, cordCreateBtn.y, assets.get(0), () -> {
            handler.getGameClient().createRoom();
            joinRoom();
        });

        newRoomBtn.setText("CREATE NEW ROOM");
        newRoomBtn.setHover(assets.get(1), "");

        UIButton multiplayerBtn = StaticElements.multiplayerBtn(this, handler, 20, height - 38);

        uiManager.addObjects(joinCodeInput, newRoomBtn, multiplayerBtn);
    }

    private void joinRoom() {
        error = handler.getGameClient().getLastError();
        if (error != null) {
            return;
        }

        handler.getGameClient().joinToRoomWithSocket();
        error = handler.getGameClient().getLastError();
        if (error != null) {
            return;
        }

        setCurrentState(handler.getGame().multiplayerState);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(3, 39, 78, 198));
        g.fillRect(0, 0, width, height);

        if (error != null) {
            Text.drawString(g, error.getErrorMessage(),
                    cordTextError.x,
                    cordTextError.y,
                    true,
                    Color.red,
                    Assets.getFont(Assets.FontsName.SLKSCR, 20));
        }

        Text.drawString(
                g,
                "JOIN TO A PRIVATE ROOM",
                cordText1.x,
                cordText1.y,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.JOYSTIX, 24)
        );

        g.drawLine(60, cordText2.y - 40, width - 60, cordText2.y - 40);

        Text.drawString(
                g,
                "CREATE A PRIVATE ROOM",
                cordText2.x,
                cordText2.y,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.JOYSTIX, 24)
        );

        uiManager.render(g);
    }
}

package com.uninorte.base.game.states;

import com.uninorte.base.api.models.Room;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.ui.ActionListener;
import com.uninorte.base.game.ui.UIButton;
import com.uninorte.base.game.ui.UIInput;
import com.uninorte.base.game.ui.UIObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RoomsState extends State {

    private ArrayList<BufferedImage> assets;

    public RoomsState(Handler handler) {
        super(handler);

       assets = Assets.getUiComponents(Assets.UI_ELEMENTS.BUTTONS_NON_SQUARE);
    }

    @Override
    protected void initComponents() {
        int width = handler.boardDimensions().width;
        int height = handler.boardDimensions().height;

        UIButton newRoomBtn = new UIButton(this, width * 0.5f, height * 0.5f, assets.get(0), () -> {
            handler.getGameClient().createRoom();
            List<Room> rooms = handler.getGameClient().getRooms();

            if (rooms != null) {
                System.out.println("\nPRINTING");
                rooms.forEach(room -> System.out.println(room.toString()));
            }
        });

        newRoomBtn.setText("CREATE NEW ROOM");
        newRoomBtn.setHover(assets.get(1), "");

        UIInput joinCodeInput = new UIInput(this, width * 0.5f, UIObject.getHeightRelative(newRoomBtn) + 20);
        joinCodeInput.setListener(new ActionListener() {
            @Override
            public void actionPerformed() {
                handler.getGameClient().joinToRoom(joinCodeInput.getText().toUpperCase());
                handler.getGameClient().getUsersFromARoom(joinCodeInput.getText().toUpperCase()).forEach(user -> {
                    System.out.println(user.toString());
                });
            }
        });

        uiManager.addObjects(newRoomBtn, joinCodeInput);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}

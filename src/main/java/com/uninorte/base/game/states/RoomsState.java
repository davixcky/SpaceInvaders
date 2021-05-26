package com.uninorte.base.game.states;

import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
            handler.getRoomRequest().createRoom(handler.getUserRequest().getCurrentUser());

            System.out.println(handler.getRoomRequest().getCurrentRoom().toString());
        });

        newRoomBtn.setText("CREATE NEW ROOM");
        newRoomBtn.setHover(assets.get(1), "");

        uiManager.addObjects(newRoomBtn);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        uiManager.render(g);
    }
}

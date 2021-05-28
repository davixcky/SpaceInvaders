package com.uninorte.base.game.states;

import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.gfx.Assets;
import com.uninorte.base.game.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class WaitingRoomState extends State {

    private Room room;

    int width = handler.boardDimensions().width;
    int height = handler.boardDimensions().height;

    private final Point cordJoinCodeText = new Point((int) (width * 0.5f), (int) (height * 0.15f));

    public WaitingRoomState(Handler handler) {
        super(handler);
    }

    @Override
    protected void initComponents() {
    }

    @Override
    public void update() {
        room = handler.getMultiplayerController().getRoom();
    }

    @Override
    public void render(Graphics g) {
        float x = 20;
        float y = 20;

        List<User> users = handler.getMultiplayerController().getUsers();
        if (users != null) {
            for (User user : users) {
                renderPlayer(g, x, y, user);
                x += 80;
                y += 0;
            }
        }

        renderJoinCodeText(g);
    }

    private void renderJoinCodeText(Graphics g) {
        if (room == null)
            return;

        Text.drawString(
                g,
                room.getJoinCode(),
                cordJoinCodeText.x,
                cordJoinCodeText.y,
                true,
                Color.white,
                Assets.getFont(Assets.FontsName.JOYSTIX, 24)
        );
    }

    private void renderPlayer(Graphics g, float x, float y, User user) {
        BufferedImage image = user.getAvatarBuffered();

        g.drawImage(image, (int) x, (int) y, null);
    }
}

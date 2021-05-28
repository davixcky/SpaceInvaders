package com.uninorte.base.game.board;

import com.uninorte.base.api.GameClient;
import com.uninorte.base.api.models.RemotePlayer;
import com.uninorte.base.game.Handler;

import java.awt.*;

public class MultiplayerBoard {

    private Handler handler;
    private GameClient gameClient;

    private RemotePlayer localPlayer, remotePlayer;

    public MultiplayerBoard(Handler handler) {
        this.handler = handler;

        gameClient = handler.getGameClient();
    }

    public void update() {
        if (localPlayer == null || remotePlayer == null)
            return;

        localPlayer.getPlayer().update();
        updatePlayers();

        handler.getGameClient().updateUser(localPlayer);
    }

    public void render(Graphics g) {
        if (localPlayer == null || remotePlayer == null)
            return;

        drawPlayers(g);
    }

    private void drawPlayers(Graphics g) {
        g.drawImage(remotePlayer.getUser().getAvatarBuffered(), (int) remotePlayer.getX(), (int) remotePlayer.getY(), null);
        g.drawImage(localPlayer.getUser().getAvatarBuffered(), (int) localPlayer.getX(), (int) localPlayer.getY(), null);
    }


    public void updatePlayers() {
        localPlayer.setX(localPlayer.getPlayer().getX());
        localPlayer.setY(localPlayer.getPlayer().getY());
    }

    public void setPlayers(RemotePlayer player1, RemotePlayer player2) {
        System.out.println("setting players");
        if (player1.getUser().getId().equals(handler.getGameClient().getCurrentUser().getId())) {
            localPlayer = player1;
            remotePlayer = player2;
            return;
        }

        localPlayer = player2;
        remotePlayer = player1;
    }

    public void updatePlayer(RemotePlayer player) {
        System.out.println("updating");
        remotePlayer.setX(player.getX());
        remotePlayer.setY(player.getY());
    }

}

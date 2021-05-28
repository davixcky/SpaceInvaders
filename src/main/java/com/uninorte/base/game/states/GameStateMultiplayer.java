package com.uninorte.base.game.states;

import com.uninorte.base.api.models.RemotePlayer;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.board.MultiplayerBoard;

import java.awt.*;
import java.util.List;

public class GameStateMultiplayer extends State {

    public MultiplayerBoard multiplayerBoard;

    public GameStateMultiplayer(Handler handler) {
        super(handler);

        multiplayerBoard = new MultiplayerBoard(handler);
    }

    @Override
    protected void initComponents() {
        handler.getGame().changeTitle("Multiplayer");

        if (handler.getMultiplayerController().getRoom().getOwnerId() == Integer.parseInt(handler.getGameClient().getCurrentUser().getId())) {
            List<User> users = handler.getMultiplayerController().getUsers();
            RemotePlayer player1 = new RemotePlayer(users.get(0), handler.boardDimensions().width * 0.4f, (float) (handler.boardDimensions().getHeight() * 0.9f));
            RemotePlayer player2 = new RemotePlayer(users.get(1), handler.boardDimensions().width * 0.7f, (float) (handler.boardDimensions().getHeight() * 0.9f));

            handler.getGameClient().initializeUsers(player1, player2);
        }
    }

    @Override
    public void update() {
        multiplayerBoard.update();
    }

    @Override
    public void render(Graphics g) {
        multiplayerBoard.render(g);
    }
}

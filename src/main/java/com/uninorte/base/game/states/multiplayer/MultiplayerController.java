package com.uninorte.base.game.states.multiplayer;

import com.uninorte.base.api.SocketActionsListener;
import com.uninorte.base.api.models.RemotePlayer;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.states.GameStateMultiplayer;
import com.uninorte.base.game.states.State;

import java.util.List;

public class MultiplayerController implements SocketActionsListener {

    private List<User> users;
    private Room room;

    public String countDown;

    private Handler handler;

    public MultiplayerController(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onUserJoined(Room room, List<User> users) {
        this.room = room;
        this.users = users;
    }

    @Override
    public void onNewUser(List<User> users, User newUser) {
        this.users = users;
    }

    @Override
    public void onCountDown(String counter) {
        countDown = counter;
    }

    @Override
    public void onStartMatch() {
        State.setCurrentState(handler.getGame().gameStateMultiplayer);
    }

    @Override
    public void onRoomInitialized(RemotePlayer player1, RemotePlayer player2) {
        player1.setHandler(handler);
        player2.setHandler(handler);

        ((GameStateMultiplayer) handler.getGame().gameStateMultiplayer).multiplayerBoard.setPlayers(player1, player2);
    }

    @Override
    public void onPlayerUpdates(RemotePlayer player) {
        ((GameStateMultiplayer) handler.getGame().gameStateMultiplayer).multiplayerBoard.updatePlayer(player);
    }

    public List<User> getUsers() {
        return users;
    }

    public Room getRoom() {
        return room;
    }
}


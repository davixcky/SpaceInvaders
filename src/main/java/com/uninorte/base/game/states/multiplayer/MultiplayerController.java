package com.uninorte.base.game.states.multiplayer;

import com.uninorte.base.api.SocketActionsListener;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.states.State;

import java.util.List;

public class MultiplayerController implements SocketActionsListener {

    private List<User> users;
    private Room room;

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
    public void onStartMatch() {
        //TODO: Start match here
    }

    public List<User> getUsers() {
        return users;
    }

    public Room getRoom() {
        return room;
    }
}


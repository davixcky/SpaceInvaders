package com.uninorte.base.api;

import com.uninorte.base.api.models.RemotePlayer;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;

import java.util.List;

public interface SocketActionsListener {
    void onUserJoined(Room room, List<User> users);
    void onNewUser(List<User> users, User newUser);
    void onCountDown(String counter);
    void onStartMatch();
    void onRoomInitialized(RemotePlayer player1, RemotePlayer player2);
    void onPlayerUpdates(RemotePlayer player);
}

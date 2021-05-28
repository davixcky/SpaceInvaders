package com.uninorte.base.api;

import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;

import java.util.List;

public interface SocketActionsListener {
    void onUserJoined(Room room, List<User> users);
    void onNewUser(List<User> users, User newUser);
    void onStartMatch();
}

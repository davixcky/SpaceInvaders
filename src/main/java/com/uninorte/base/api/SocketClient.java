package com.uninorte.base.api;

import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


class SocketClient {

    private static final String JOIN_ROOM = "join-room";

    private Socket socket;

    private List<SocketActionsListener> listeners = new ArrayList<>();

    protected SocketClient(String socketUrl, String userId) {
        createSocket(socketUrl, userId);
        initListeners();
    }

    public void connect() {
        socket.connect();
    }

    public void subscribe(SocketActionsListener toAdd) {
        listeners.add(toAdd);
    }

    private void createSocket(String socketUrl, String userId) {
        URI uri = URI.create(socketUrl);
        IO.Options options = IO.Options.builder()
                .setForceNew(true)
                .setQuery("userId="+userId)
                .build();

        socket = IO.socket(uri, options);
    }

    private void initListeners() {
        socket.on("user-joined", this::onUserJoinedCallback);
        socket.on("new-user", this::onNewUserCallback);
        socket.on("start-match", this::onStartMatchCallback);
    }

    private void onUserJoinedCallback(Object ...objects) {
        Room room = Room.createFromJson(objects[0].toString());
        List<User> users = User.createUsersFromJson(objects[1].toString());

        for (SocketActionsListener listener: listeners)
            listener.onUserJoined(room, users);
    }

    private void onNewUserCallback(Object ...objects) {
        List<User> users = User.createUsersFromJson(objects[0].toString());
        User newUser = User.createFromJson(objects[1].toString());

        for (SocketActionsListener listener: listeners)
            listener.onNewUser(users, newUser);
    }

    private void onStartMatchCallback(Object ...objects) {
        for (SocketActionsListener listener: listeners)
            listener.onStartMatch();
    }

    public void joinRoom(User user, Room room) {
        socket.emit(JOIN_ROOM, user.toJson(), room.toJson());
    }

}

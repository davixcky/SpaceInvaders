package com.uninorte.base.api;

import com.uninorte.base.api.models.RemotePlayer;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


class SocketClient {

    private static final String JOIN_ROOM = "join-room";
    private static final String INITIALIZE_ROOM = "initialize-room";
    private static final String UPDATE_USER = "remote-send";


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
        socket.on("count-down", this::onCountDown);
        socket.on("room-initialized", this::onRoomInitialized);
        socket.on("player-updated", this::onPlayerUpdated);
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

    private void onCountDown(Object ...objects) {
        for (SocketActionsListener listener: listeners)
            listener.onCountDown(objects[0].toString());
    }

    private void onStartMatchCallback(Object ...objects) {
        for (SocketActionsListener listener: listeners)
            listener.onStartMatch();
    }

    private void onRoomInitialized(Object ...objects) {
        RemotePlayer remotePlayer1 = RemotePlayer.createFromJson(objects[0].toString());
        RemotePlayer remotePlayer2 = RemotePlayer.createFromJson(objects[1].toString());

        for (SocketActionsListener listener: listeners)
            listener.onRoomInitialized(remotePlayer1, remotePlayer2);
    }

    private void onPlayerUpdated(Object ...objects) {
        RemotePlayer remotePlayer = RemotePlayer.createFromJson(objects[0].toString());

        for (SocketActionsListener listener: listeners)
            listener.onPlayerUpdates(remotePlayer);
    }

    public void joinRoom(User user, Room room) {
        socket.emit(JOIN_ROOM, user.toJson(), room.toJson());
    }

    public void initializeMatch(String jsonUser1, String jsonUser2) {
        socket.emit(INITIALIZE_ROOM, jsonUser1, jsonUser2);
    }

    public void updateUser(String toJson) {
        socket.emit(UPDATE_USER, toJson);
    }
}

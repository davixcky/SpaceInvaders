package com.uninorte.base.api;

import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URI;


class SocketClient {

    private static final String JOIN_ROOM = "join-room";

    private Socket socket;

    protected SocketClient(String socketUrl, String userId) {
        createSocket(socketUrl, userId);
        initListeners();
    }

    public void connect() {
        socket.connect();
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
        socket.on("user-joined", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("User joined");
                for (Object object : objects) {
                    System.out.println(object);
                }
                System.out.println();
            }
        });

        socket.on("new-user", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("User addded");
                for (Object object : objects) {
                    System.out.println(object);
                }
                System.out.println();
            }
        });
    }

    public void joinRoom(User user, Room room) {
        socket.emit(JOIN_ROOM, user.toJson(), room.toJson());
    }
}

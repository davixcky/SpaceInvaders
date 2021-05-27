package com.uninorte.base.api;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URI;


class SocketClient {

    private Socket socket;

    protected SocketClient(String socketUrl) {
        System.out.println("creating");
        createSocket(socketUrl);
    }

    private void createSocket(String socketUrl) {
        URI uri = URI.create(socketUrl);
        IO.Options options = IO.Options.builder()
                .setForceNew(true)
                .build();

        socket = IO.socket(uri, options);

        socket.on("connect", new Emitter.Listener() {
            @Override
            public void call(Object... objects) {
                System.out.println("connect handler called");
            }
        });

        socket.connect();
    }
}

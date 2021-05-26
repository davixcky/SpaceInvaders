package com.uninorte.base.api;

import com.uninorte.base.api.models.Error;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;

import java.io.IOException;

public class RoomRequest extends Room {

    private static final String GET_ROOMS = "/rooms";
    private static final String CREATE_ROOM = "/rooms/create";
    private static final String JOIN_ROOM = "/rooms/join/%s";
    private static final String GET_ROOMS_USERS = "/rooms/%s/users";

    private final RequestHandler requestHandler;

    private Error error = null;
    private Room currentRoom = null;

    public RoomRequest(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void createRoom(User user) {
        RequestHandler.RequestResponse response = null;
        try {
            response = requestHandler.postRequest(CREATE_ROOM, user.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = response.bodyResponse;
        error = null;
        if (response.statusCode >= 400) {
            error = Error.createFromJson(json);
            return;
        }

        currentRoom = Room.createFromJson(json);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Error getError() {
        return error;
    }
}

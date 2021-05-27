package com.uninorte.base.api;

import com.uninorte.base.api.models.Error;
import com.uninorte.base.api.models.Room;
import com.uninorte.base.api.models.User;

import java.io.IOException;
import java.util.List;

public class GameClient {

    private static final String USER_CREATE = "/users";
    private static final String ROOMS_GET = "/rooms";
    private static final String ROOMS_CREATE = "/rooms/create";
    private static final String ROOMS_JOIN = "/rooms/join/%s";
    private static final String ROOMS_GET_USERS = "/rooms/%s/users";

    private static final Error ERR_USER_NOT_LOGGED_IN = new Error("user needs to be logged in");

    private User currentUser;
    private Room currentRoom;
    private List<Room> allRooms;
    private Error lastError;

    private SocketClient socketClient;

    private RequestHandler requestHandler;

    public GameClient(String basePath) {
        requestHandler = new RequestHandler(basePath);

        currentRoom = null;
        currentUser = null;
        allRooms = null;
        lastError = null;

    }

    public void connectToSocket() {
        socketClient = new SocketClient("http://localhost:8080", currentUser.getId());
        socketClient.connect();
    }

    public void createUser(String nickname) {
        User tmpUser = new User(nickname);

        RequestHandler.RequestResponse response = doPost(USER_CREATE, tmpUser.toJson());
        if (response.statusCode < 400) {
            currentUser = User.createFromJson(response.bodyResponse);
            connectToSocket();
        }
    }

    public void createRoom() {
        validateUserIsLogged();
        if (lastError != null)
            return;

        RequestHandler.RequestResponse response = doPost(ROOMS_CREATE, currentUser.toJson());
        if (response.statusCode < 400) {
            currentRoom = Room.createFromJson(response.bodyResponse);
        }
    }

    public void joinToRoom(String joinCode) {
        validateUserIsLogged();
        if (lastError != null)
            return;

        String url = String.format(ROOMS_JOIN, joinCode);
        RequestHandler.RequestResponse response = doPost(url, currentUser.toJson());
        if (response.statusCode < 400) {
            currentRoom = Room.createFromJson(response.bodyResponse);
        }
    }

    public List<User> getUsersFromARoom(String joinCode) {
        String url = String.format(ROOMS_GET_USERS, joinCode);
        RequestHandler.RequestResponse response = doGet(url);
        
        List<User> users = null;
        if (response.statusCode < 400) {
            users = User.createUsersFromJson(response.bodyResponse);
        }
        
        return users;
    }
    
    public List<Room> getRooms() {
        validateUserIsLogged();
        if (lastError != null)
            return null;

        RequestHandler.RequestResponse response = doGet(ROOMS_GET);
        if (response.statusCode < 400)
            allRooms = Room.createRoomsFromJson(response.bodyResponse);

        return allRooms;
    }

    private RequestHandler.RequestResponse doPost(String endpoint, String json) {
        RequestHandler.RequestResponse response = null;
        try {
            response = requestHandler.postRequest(endpoint, json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonResult = response.bodyResponse;

        lastError = null;
        if (response.statusCode >= 400) {
            lastError = Error.createFromJson(jsonResult);
        }

        return response;
    }

    private RequestHandler.RequestResponse doGet(String endpoint) {
        RequestHandler.RequestResponse response = null;
        try {
            response = requestHandler.getRequest(endpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonResult = response.bodyResponse;

        lastError = null;
        if (response.statusCode >= 400) {
            lastError = Error.createFromJson(jsonResult);
        }

        return response;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        connectToSocket();
    }

    private void validateUserIsLogged() {
        if (currentUser == null)
            lastError = ERR_USER_NOT_LOGGED_IN;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public List<Room> getAllRooms() {
        return allRooms;
    }

    public Error getLastError() {
        return lastError;
    }

}

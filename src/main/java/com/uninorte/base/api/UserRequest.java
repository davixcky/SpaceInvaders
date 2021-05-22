package com.uninorte.base.api;

import com.uninorte.base.api.models.Error;
import com.uninorte.base.api.models.User;

import java.io.IOException;

public class UserRequest extends User {

    private static final String CREATE_USER = "/users";

    private final RequestHandler requestHandler;

    private User currentUser;
    private Error error = null;

    public UserRequest(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void createUser(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;

        User tmpUser = new User(nickname, email);

        RequestHandler.RequestResponse response = null;
        try {
            response = requestHandler.postRequest(CREATE_USER, tmpUser.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = response.bodyResponse;

        error = null;
        if (response.statusCode >= 400) {
            error = Error.createFromJson(json);
            return;
        }

        currentUser = User.createFromJson(json);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public String toJson() {
        return currentUser != null ? currentUser.toJson() : super.toJson();
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public Error getError() {
        return error;
    }
}

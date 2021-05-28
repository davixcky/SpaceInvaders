package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.uninorte.base.game.Handler;
import com.uninorte.base.game.entities.creatures.Player;

public class RemotePlayer extends Base {

    @JsonProperty("user") protected User user;
    @JsonProperty("x") protected float x;
    @JsonProperty("y") protected float y;

    @JsonIgnore() Handler handler;
    @JsonIgnore() Player player;

    public RemotePlayer(User user, float x, float y) {
        this.user = user;
        this.x = x;
        this.y = y;
    }

    public RemotePlayer() {
    }

    @JsonIgnore
    public Player getPlayer() {
        if (handler == null)
            return null;

        if (player == null)
            player = new Player(handler, x, y);
        return player;
    }

    public User getUser() {
        return user;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public static RemotePlayer createFromJson(String json) {
        return (RemotePlayer) new RemotePlayer().fromJson(json);
    }

}

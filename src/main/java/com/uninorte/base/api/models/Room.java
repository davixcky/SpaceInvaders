package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class Room extends Base {

    @JsonProperty("id") protected String id;
    @JsonProperty("title") protected String title;
    @JsonProperty("ownerId") protected int ownerId;
    @JsonProperty("joinCode") protected String joinCode;
    @JsonProperty("maxParticipants") protected int maxParticipants;
    @JsonProperty("totalParticipants") protected int totalParticipants;

    public Room(String id, String title, int ownerId, String joinCode, int maxParticipants, int totalParticipants) {
        this.id = id;
        this.title = title;
        this.ownerId = ownerId;
        this.joinCode = joinCode;
        this.maxParticipants = maxParticipants;
        this.totalParticipants = totalParticipants;
    }

    public Room() {
    }

    public static Room createFromJson(String json) {
        return (Room) new Room().fromJson(json);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", joinCode='" + joinCode + '\'' +
                ", maxParticipants=" + maxParticipants +
                ", totalParticipants=" + totalParticipants +
                '}';
    }

    @Override
    public void render(Graphics g, float x, float y, boolean center) {

    }
}

package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.util.List;

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

    public static java.util.List<Room> createRoomsFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        java.util.List<Room> roomList = null;
        try {
            roomList = mapper.readValue(json, new TypeReference<List<Room>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return roomList;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public int getTotalParticipants() {
        return totalParticipants;
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

package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AlienCoordinates extends Base {

    @JsonProperty("x") protected float x;
    @JsonProperty("y") protected float y;
    @JsonProperty("unique_position") protected int uniquePosition;
    @JsonProperty("active") protected boolean isActive;
    @JsonProperty("isRenderingExplosion") boolean isRenderingExplosion;

    public AlienCoordinates(float x, float y, int uniquePosition, boolean isActive, boolean isRenderingExplosion) {
        this.x = x;
        this.y = y;
        this.uniquePosition = uniquePosition;
        this.isActive = isActive;
        this.isRenderingExplosion = isRenderingExplosion;
    }

    public AlienCoordinates() {

    }

    public static AlienCoordinates createFromJson(String json) {
        return  (AlienCoordinates) new AlienCoordinates().fromJson(json);
    }

    public static List<AlienCoordinates> createAliensCoordinatesFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<AlienCoordinates> aliens = null;
        try {
            aliens = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return aliens;
    }

    private static ObjectNode createAlienNode(AlienCoordinates alienCoordinates, ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("x", alienCoordinates.x);
        node.put("y", alienCoordinates.y);
        node.put("unique_position", alienCoordinates.uniquePosition);
        node.put("active", alienCoordinates.isActive);
        node.put("isRenderingExplosion", alienCoordinates.isRenderingExplosion);

        return node;
    }

    public static String aliensCoordinatesToJson(ArrayList<AlienCoordinates> aliensCoordinates) {
        if (aliensCoordinates == null || aliensCoordinates.size() == 0)
            return null;

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode nodes = mapper.createArrayNode();
        for (AlienCoordinates alienCoordinates : aliensCoordinates) {
            nodes.add(createAlienNode(alienCoordinates, mapper));
        }

        String json = null;
        try {
            json = mapper.writeValueAsString(nodes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getUniquePosition() {
        return uniquePosition;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isRenderingExplosion() {
        return isRenderingExplosion;
    }

    @Override
    public String toString() {
        return "AlienCoordinates{" +
                "x=" + x +
                ", y=" + y +
                ", uniquePosition=" + uniquePosition +
                '}';
    }

}

package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.smiley.SmileyAvatar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class User extends Base {
    @JsonProperty("id") protected String id;
    @JsonProperty("nickname") protected String nickname;

    @JsonIgnore() protected BufferedImage avatarBuffered;

    public User(String id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public User() {

    }

    public static User createFromJson(String json) {
        User tmp = (User) new User().fromJson(json);
        tmp.avatarBuffered = tmp.generateAvatar(Long.parseLong(tmp.getId()));
        return tmp;
    }

    public static List<User> createUsersFromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = null;
        try {
            users = mapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (users != null)
                users.forEach(user -> user.avatarBuffered = user.generateAvatar(Long.parseLong(user.id)));

        return users;
    }

    private BufferedImage generateAvatar(long id) {
        Avatar avatar = SmileyAvatar.newGhostAvatarBuilder().build();
        return avatar.create(id);
    }

    public BufferedImage getAvatarBuffered() {
        if (avatarBuffered == null)
            avatarBuffered = generateAvatar(Long.parseLong(this.getId()));

        return avatarBuffered;
    }

    public String getId() {
        return this.id;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarBuffered=" + avatarBuffered +
                '}';
    }

    public void render(Graphics g, float x, float y, boolean center) {
        if (avatarBuffered == null)
            return;

        if  (center) {
            x = x - (avatarBuffered.getWidth() >> 1);
        }

        g.drawImage(avatarBuffered,  (int) x, (int) y,null);
    }
}
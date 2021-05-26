package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.smiley.SmileyAvatar;

import java.awt.*;
import java.awt.image.BufferedImage;

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

        Avatar avatar = SmileyAvatar.newGhostAvatarBuilder().build();
        tmp.avatarBuffered = avatar.create(Long.parseLong(tmp.getId()));

        return tmp;
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

    @Override
    public void render(Graphics g, float x, float y, boolean center) {
        if (avatarBuffered == null)
            return;

        if  (center) {
            x = x - (avatarBuffered.getWidth() >> 1);
        }

        g.drawImage(avatarBuffered,  (int) x, (int) y,null);
    }
}
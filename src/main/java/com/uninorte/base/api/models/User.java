package com.uninorte.base.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.smiley.SmileyAvatar;

import java.awt.*;
import java.awt.image.BufferedImage;

@JsonIgnoreProperties(value = { "oauthID", "provider" })
public class User extends Base {
    @JsonProperty("id") protected String id;
    @JsonProperty("nickname") protected String nickname;
    @JsonProperty("email") protected String email;
    @JsonProperty("avatarProfile") protected String avatarUrl;

    @JsonIgnore() protected BufferedImage avatarBuffered;

    public User(String id, String nickname, String email, String avatarUrl) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public User(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
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

    public String getAvatarUrl() throws Exception {
        return this.avatarUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
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
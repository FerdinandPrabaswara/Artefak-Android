package com.example.thesis3.model;

public class Post {
    private String username;
    private String content;
    private int profileResId; // resource ID untuk foto profil

    public Post(String username, String content, int profileResId) {
        this.username = username;
        this.content = content;
        this.profileResId = profileResId;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public int getProfileResId() {
        return profileResId;
    }
}


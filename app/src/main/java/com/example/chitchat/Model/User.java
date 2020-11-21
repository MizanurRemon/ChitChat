package com.example.chitchat.Model;

public class User {

    private String name;
    private String imageURL;
    private String id;

    public User() {

    }

    public User(String name, String imageURL, String id) {
        this.name = name;
        this.imageURL = imageURL;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

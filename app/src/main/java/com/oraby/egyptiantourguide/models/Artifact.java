package com.oraby.egyptiantourguide.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Artifact {

    private long Id;
    private String Name;
    private String Adress;
    private String Photo;
    private String Description;
    private long likes;

    public Artifact() {
    }

    public Artifact(long id, String name, String adress, String photo, String description, long likes) {
        Id = id;
        Name = name;
        Adress = adress;
        Photo = photo;
        Description = description;
        this.likes = likes;
    }

    public Artifact(String name, String adress, String photo, String description, long likes) {

        Name = name;
        Adress = adress;
        Photo = photo;
        Description = description;
        this.likes = likes;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("Id", Id);
        result.put("Name", Name);
        result.put("Adress", Adress);
        result.put("Photo", Photo);
        result.put("Description", Description);
        result.put("likes", likes);

        return result;
    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }
}

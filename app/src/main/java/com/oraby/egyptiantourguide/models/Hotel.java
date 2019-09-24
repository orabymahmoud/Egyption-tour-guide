package com.oraby.egyptiantourguide.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Hotel {

    private long Id;
    private String Name;
    private String Adress;
    private String Photo;
    private String Url;
    private String Description;
    private long Likes;

    public Hotel() {
    }

    public Hotel(long id, String name, String adress, String photo, String url, String description, long likes) {
        Id = id;
        Name = name;
        Adress = adress;
        Photo = photo;
        Url = url;
        Description = description;
        Likes = likes;
    }

    public Hotel(String name, String adress, String photo, String url, String description, long likes) {
        Name = name;
        Adress = adress;
        Photo = photo;
        Url = url;
        Description = description;
        Likes = likes;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("Id", Id);
        result.put("Name", Name);
        result.put("Adress", Adress);
        result.put("Photo", Photo);
        result.put("Url", Url);
        result.put("Description", Description);
        result.put("Likes", Likes);

        return result;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getId() {
        return Id;
    }

    public long getLikes() {
        return Likes;
    }

    public void setLikes(long likes) {
        Likes = likes;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

package com.oraby.egyptiantourguide.models;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class User  {

    private String Name;
    private String Mobile;
    private String Password;
    private List<Artifact> likesarts = new ArrayList<>();
    private List<Hotel> likeshotels = new ArrayList<>();
    private List<Resturant> likesresturants = new ArrayList<>();

    @Inject
    public User() {
    }

    public User(String name, String mobile, String password, List<Artifact> likesarts, List<Hotel> likeshotels, List<Resturant> likesresturants) {
        Name = name;
        Mobile = mobile;
        Password = password;
        this.likesarts = likesarts;
        this.likeshotels = likeshotels;
        this.likesresturants = likesresturants;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public List<Artifact> getLikesarts() {
        return likesarts;
    }

    public void setLikesarts(List<Artifact> likesarts) {
        this.likesarts = likesarts;
    }

    public List<Hotel> getLikeshotels() {
        return likeshotels;
    }

    public void setLikeshotels(List<Hotel> likeshotels) {
        this.likeshotels = likeshotels;
    }

    public List<Resturant> getLikesresturants() {
        return likesresturants;
    }

    public void setLikesresturants(List<Resturant> likesresturants) {
        this.likesresturants = likesresturants;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("Name", Name);
        result.put("Mobile", Mobile);
        result.put("Password", Password);
        result.put("likesarts", likesarts);
        result.put("likeshotels", likeshotels);
        result.put("likesresturants", likesresturants);

        return result;
    }
}

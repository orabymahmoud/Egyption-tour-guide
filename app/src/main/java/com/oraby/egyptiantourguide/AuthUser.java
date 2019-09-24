package com.oraby.egyptiantourguide;

import com.oraby.egyptiantourguide.models.User;

public class AuthUser {

    public static User user = new User();

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AuthUser.user = user;
    }
}

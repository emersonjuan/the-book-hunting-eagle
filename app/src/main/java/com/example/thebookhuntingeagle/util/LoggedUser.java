package com.example.thebookhuntingeagle.util;

import com.example.thebookhuntingeagle.model.User;

public class LoggedUser {

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        LoggedUser.user = user;
    }
}

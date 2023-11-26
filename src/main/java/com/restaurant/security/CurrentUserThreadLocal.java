package com.restaurant.security;

import com.restaurant.entities.User;

public class CurrentUserThreadLocal {
    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    private CurrentUserThreadLocal() {

    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}
package com.example.evan_mulcare_assignment_two.Models;

public class CurrentUserSingleton {
    private static CurrentUserSingleton instance;
    private User currentUser;

    private CurrentUserSingleton() {
    }

    public static synchronized CurrentUserSingleton getInstance() {
        if (instance == null) {
            instance = new CurrentUserSingleton();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void clearCurrentUser() {
        this.currentUser = null;
    }
}

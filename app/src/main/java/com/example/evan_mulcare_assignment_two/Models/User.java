package com.example.evan_mulcare_assignment_two.Models;

public class User {
    private int userId;
    private String username;
    private String password;
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

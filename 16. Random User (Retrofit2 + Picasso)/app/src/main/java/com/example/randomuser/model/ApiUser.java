package com.example.randomuser.model;

public class ApiUser {
    User data;

    public ApiUser() {
    }

    public ApiUser(User data) {
        this.data = data;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}

package com.example.antlerros95.chatroom;

/**
 * Created by antlerros95 on 15/10/2016.
 */

public class User {
    private String id;
    private String status;

    public User() {
        this.id = "";
        this.status = "";

    }

    public User(String _id, String _status) {
        this.id = _id;
        this.status = _status;
    }

    public String getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }
}

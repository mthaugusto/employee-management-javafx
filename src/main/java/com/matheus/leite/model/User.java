package com.matheus.leite.model;

public class User {
    
    public int id;
    public String username;
    public String password;

    public User() {
    
    }
    
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}

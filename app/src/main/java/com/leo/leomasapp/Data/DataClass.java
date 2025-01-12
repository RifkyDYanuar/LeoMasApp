package com.leo.leomasapp.Data;

import java.io.Serializable;

public class DataClass implements Serializable {
    private String name;
    private  String email;
     private  String username ;
    private String password;

    public DataClass(){

    }
    public DataClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public DataClass(String username, String name, String email) {
        this.name = name;
        this.email = email;
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

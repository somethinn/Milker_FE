package com.example.milkerfe.model;

import org.json.JSONObject;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userName; // Added for login
    private String password;

    public User(String firstName, String lastName, String phoneNumber, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    public JSONObject toJson() throws Exception {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("phoneNumber", phoneNumber);
        json.put("password", password);
        return json;
    }

    public JSONObject toLoginJson() throws Exception {
        JSONObject json = new JSONObject();
        json.put("userName", userName);
        json.put("password", password);
        return json;
    }

    public String getUserName() { return userName; }
}
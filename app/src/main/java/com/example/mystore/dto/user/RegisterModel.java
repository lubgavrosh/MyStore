package com.example.mystore.dto.user;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class RegisterModel {
    private String email;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    public RegisterModel() {
        // Конструктор за замовчуванням
    }

    public RegisterModel(String email, String userName, String password, String firstName, String lastName) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Map<String, RequestBody> toMap() {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("email", createPartFromString(email));
        map.put("userName", createPartFromString(userName));
        map.put("password", createPartFromString(password));
        map.put("firstName", createPartFromString(firstName));
        map.put("lastName", createPartFromString(lastName));
        return map;
    }

    private RequestBody createPartFromString(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

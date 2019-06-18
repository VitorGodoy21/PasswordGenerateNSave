package com.example.myapplication.model;

public class PasswordModel {

    private String Title;
    private String Username;
    private String Password;
    private String Description;
    private String Date;

    public PasswordModel(String title, String username, String password, String description, String date) {
        Title = title;
        Username = username;
        Password = password;
        Description = description;
        Date = date;
    }

    public PasswordModel() {
        Title = "";
        Username = "";
        Password = "";
        Description = "";
        Date = "";
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return Title;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getDescription() {
        return Description;
    }

    public String getDate() {
        return Date;
    }
}

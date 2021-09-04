package com.example.wk01hw02solo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wk01hw02solo.db.AppDatabase;

import java.util.Date;


@Entity(tableName = AppDatabase.USER_TABLE_NAME)
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String username;
    private String password;
    private Date date;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.date = new Date();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder("Username: " + username + ",\n" + "Password: " + password);
        output.append(",\nDateCreated: ").append(getDate());

        return output.toString();
    }
}

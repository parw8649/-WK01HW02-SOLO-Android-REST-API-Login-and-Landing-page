package com.example.wk01hw02solo.model;

public class UserPostData {

    private int id;
    private int userId;
    private String title;
    private String body;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "UserId: " + userId + "\n" +
                "title: " + title + "\n" +
                "body: " + body;
    }
}

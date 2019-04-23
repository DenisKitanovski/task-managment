package com.example.springwebproektna.model;

import org.springframework.data.annotation.Id;

public class Activity {
    @Id
    private String id;
    private String action;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

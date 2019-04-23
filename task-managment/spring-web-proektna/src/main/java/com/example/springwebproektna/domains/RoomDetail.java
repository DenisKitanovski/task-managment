package com.example.springwebproektna.domains;

public class RoomDetail {
    private String id;

    private String name;

    private String connectedUser;

    private String owner;

    private boolean currentUserIsOwner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(String connectedUser) {
        this.connectedUser = connectedUser;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isCurrentUserIsOwner() {
        return currentUserIsOwner;
    }

    public void setCurrentUserIsOwner(boolean currentUserIsOwner) {
        this.currentUserIsOwner = currentUserIsOwner;
    }
}

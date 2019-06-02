package com.example.springwebproektna.domains;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewColumnAndPos {
    @JsonProperty
    private String id;
    @JsonProperty
    private String oldColumn;
    @JsonProperty
    private String oldPosition;
    @JsonProperty
    private String newColumn;
    @JsonProperty
    private String newPosition;

    public String getOldColumn() {
        return oldColumn;
    }

    public void setOldColumn(String oldColumn) {
        this.oldColumn = oldColumn;
    }

    public String getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(String newColumn) {
        this.newColumn = newColumn;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(String oldPosition) {
        this.oldPosition = oldPosition;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }
}

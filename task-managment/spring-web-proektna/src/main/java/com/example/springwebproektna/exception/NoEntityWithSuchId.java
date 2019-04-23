package com.example.springwebproektna.exception;


public class NoEntityWithSuchId extends RuntimeException {
    public NoEntityWithSuchId() {
        super("No Room With Such Id");
    }
}
package com.example.springwebproektna.exception;

public class UserWithSuchUsernameAlreadyExists extends RuntimeException {

    public UserWithSuchUsernameAlreadyExists(){
        super("User With Such Username Already Exists");
    }
}


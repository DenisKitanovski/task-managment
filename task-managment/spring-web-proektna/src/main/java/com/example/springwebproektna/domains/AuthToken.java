package com.example.springwebproektna.domains;

import com.example.springwebproektna.model.User;

public class AuthToken {

    private String token;
    private User user;
    private String companyName;

    public AuthToken(){

    }

    public AuthToken(String token, User user, String companyName){
        this.token = token;
        this.user = user;
        this.companyName = companyName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

package com.example.springwebproektna.model;

import org.springframework.data.annotation.Id;

public class Dashboard {

    @Id
    private String id;

    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

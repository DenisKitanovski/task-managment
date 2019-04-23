package com.example.springwebproektna.service;

import com.example.springwebproektna.model.Activity;

import java.util.List;

public interface ActivitySevice {

    Activity save(Activity activity);
    List<Activity> findAll();

}

package com.example.springwebproektna.controller;

import com.example.springwebproektna.model.Activity;
import com.example.springwebproektna.service.ActivitySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {
    private ActivitySevice activitySevice;

    @Autowired
    public ActivityController(ActivitySevice activitySevice) {
        this.activitySevice = activitySevice;
    }

    @PostMapping("/activity/save")
    public Activity save(@RequestBody Activity activity){
        return activitySevice.save(activity);
    }

    @GetMapping("/activity/all")
    public List<Activity> findAll(){
        return activitySevice.findAll();
    }
}

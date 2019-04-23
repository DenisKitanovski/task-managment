package com.example.springwebproektna.controller;

import com.example.springwebproektna.domains.Progress;
import com.example.springwebproektna.model.Task;
import com.example.springwebproektna.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task/save")
    public Task saveTask(@RequestBody Task task) throws ParseException {
        return taskService.saveTask(task);
    }

    @PostMapping("/channel/{id}")
    public List<Task> findByChannelId( @PathVariable("id") String id){
        return taskService.findByChannelId(id);
    }

    @PostMapping("/progress")
    public Task findByTaskId(@RequestBody Progress progress){
        Task task =  taskService.findById(progress.getId());
        task.setProgressBar(progress.getProgressBar());
        taskService.saveTask(task);
        return task;

    }

    @DeleteMapping("/delete/{id}")
    public void deleteByTaskId(@PathVariable("id") String id){
        taskService.deleteByTaskId(id);
    }
}

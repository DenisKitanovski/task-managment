package com.example.springwebproektna.controller;

import com.example.springwebproektna.domains.NewColumnAndPos;
import com.example.springwebproektna.domains.Progress;
import com.example.springwebproektna.model.Task;
import com.example.springwebproektna.service.TaskService;
import com.example.springwebproektna.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@RestController
public class TaskController {
    private TaskServiceImpl taskService;
    private SimpMessagingTemplate template;

    @Autowired
    public TaskController(TaskServiceImpl taskService, SimpMessagingTemplate template) {
        this.taskService = taskService;
        this.template = template;
    }

    @PostMapping("/task/save")
    public Task saveTask(@RequestBody Task task) throws ParseException {
        return taskService.saveTask(task);
    }

    @PostMapping("/channel/{id}")
    public List<Task> findByChannelId( @PathVariable("id") String id){
        return taskService.findAllByChannelId(id);
    }

    @PostMapping("/progress")
    public Task findByTaskId(@RequestBody Progress progress){
        Task task =  taskService.findById(progress.getId());
        task.setProgressBar(progress.getProgressBar());
        taskService.saveTask(task);
        return task;

    }

    /*@PostMapping("/change/column/position")
    public Task changeColumnAndTaskPosition(@RequestBody NewColumnAndPos newColumnAndPos){
        return taskService.changeColumnAndTaskPosition(newColumnAndPos);
    }*/

    @DeleteMapping("/delete/{id}")
    public void deleteByTaskId(@PathVariable("id") String id){
        taskService.deleteByTaskId(id);
    }


    @MessageMapping("/task/{roomId}")
    public void onReceiveTask(@DestinationVariable String roomId, @Payload NewColumnAndPos newColumnAndPos) {
        System.out.println(newColumnAndPos.getNewColumn());
        taskService.changeColumnAndTaskPosition(newColumnAndPos);
        this.template.convertAndSend("/chat/" + roomId, newColumnAndPos);
    }
}

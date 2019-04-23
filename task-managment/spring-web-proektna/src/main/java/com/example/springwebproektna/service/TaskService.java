package com.example.springwebproektna.service;

import com.example.springwebproektna.model.Task;

import java.util.List;

public interface TaskService  {
    public Task saveTask(Task task);
    public List<Task> findByChannelId(String id);
    Task findById(String id);
    void deleteByTaskId(String id);
}

package com.example.springwebproektna.service;

import com.example.springwebproektna.model.Task;
import com.example.springwebproektna.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findByChannelId(String id) {
        return taskRepository.findByChannelId(id);
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public void deleteByTaskId(String id) {
        taskRepository.deleteById(id);
    }
}

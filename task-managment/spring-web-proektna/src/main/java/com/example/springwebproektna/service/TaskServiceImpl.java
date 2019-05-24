package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.NewColumnAndPos;
import com.example.springwebproektna.model.Task;
import com.example.springwebproektna.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl{
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public Task saveTask(Task task) {
     //   int count = Math.toIntExact(taskRepository.countByColumnAndChannelId(task.getColumn(), task.getChannelId()));
     //   task.setPosition(count);
        return taskRepository.save(task);
    }


    public List<Task> findAllByChannelId(String id) {
        return taskRepository.findAllByChannelId(id);
    }


    public Task findById(String id) {
        return taskRepository.findById(id).get();
    }

    public Task changeColumnAndTaskPosition(NewColumnAndPos newColumnAndPos) {
        Task task = taskRepository.findById(newColumnAndPos.getId()).get();
        task.setColumn(newColumnAndPos.getNewColumn());
       // task.setPosition(newColumnAndPos.getNewPosition());
        return taskRepository.save(task);
    }


    public void deleteByTaskId(String id) {
        taskRepository.deleteById(id);
    }
}

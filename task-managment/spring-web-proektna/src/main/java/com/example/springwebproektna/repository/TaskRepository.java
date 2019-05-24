package com.example.springwebproektna.repository;

import com.example.springwebproektna.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task,String> {
    List<Task> findAllByChannelId(String id);
    Optional<Task> findById(String id);
    void deleteById(String id);
    Long countByColumnAndChannelId(String column, String channelId);
}

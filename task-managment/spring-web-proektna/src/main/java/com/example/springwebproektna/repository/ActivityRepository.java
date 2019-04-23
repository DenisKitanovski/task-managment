package com.example.springwebproektna.repository;

import com.example.springwebproektna.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity,String> {
}

package com.example.springwebproektna.repository;

import com.example.springwebproektna.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room,String> {

   // List<Room> findByUserId(String id);

    List<Room> findAll();

    List<Room> findAllByDashboardId(String id);

}
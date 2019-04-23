package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.RoomDetail;
import com.example.springwebproektna.domains.RoomId;
import com.example.springwebproektna.model.Room;

import java.util.List;

public interface RoomService {
    RoomId createRoom(Room room);

    List<RoomDetail> getCurrentUserRooms();

    RoomDetail getRoom(String roomId);

    List<RoomDetail> findAllRooms();
}
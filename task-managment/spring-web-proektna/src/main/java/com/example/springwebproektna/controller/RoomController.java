package com.example.springwebproektna.controller;

import com.example.springwebproektna.domains.DashboardId;
import com.example.springwebproektna.domains.RoomDetail;
import com.example.springwebproektna.domains.RoomId;
import com.example.springwebproektna.model.Dashboard;
import com.example.springwebproektna.model.Room;
import com.example.springwebproektna.service.RoomService;
import com.example.springwebproektna.service.RoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {
    private RoomServiceImpl roomService;


    @Autowired
    public RoomController(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/me/room/create")
    public Room createRoom(@RequestBody Room room){
        return roomService.createRoom(room);
    }

    @PostMapping(path = "/me/room/all")
    public List<Room> findAllRooms(@RequestBody DashboardId dashboardId){
        return roomService.findAllRooms(dashboardId);
    }

}
package com.example.springwebproektna.controller;

import com.example.springwebproektna.domains.RoomDetail;
import com.example.springwebproektna.domains.RoomId;
import com.example.springwebproektna.model.Room;
import com.example.springwebproektna.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {
    private RoomService roomService;


    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/me/room/create")
    //@PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public RoomId createRoom(@RequestBody Room room){
        return roomService.createRoom(room);
    }

    @GetMapping("/me/room/all")
    public List<RoomDetail> findAllRooms(){
        return roomService.findAllRooms();
    }
}
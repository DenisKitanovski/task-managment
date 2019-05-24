package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.DashboardId;
import com.example.springwebproektna.domains.RoomDetail;
import com.example.springwebproektna.domains.RoomId;
import com.example.springwebproektna.exception.NoEntityWithSuchId;
import com.example.springwebproektna.model.Dashboard;
import com.example.springwebproektna.model.Room;
import com.example.springwebproektna.model.User;
import com.example.springwebproektna.repository.RoomRepository;
import com.example.springwebproektna.repository.UserRepository;
import com.example.springwebproektna.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl {

    private RoomRepository roomRepository;
    private SecurityUtils securityUtils;
    private UserRepository userRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, SecurityUtils securityUtils, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    public Room createRoom(Room room) {
       // User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin());
        return roomRepository.save(room);
    }

    public List<Room> findAllRooms(DashboardId dashboard) {
       // return roomRepository.findAll();
       return roomRepository.findAllByDashboardId(dashboard.getDashboardId());
    }

   /* @Override
    public List<RoomDetail> getCurrentUserRooms() {
        User user = userRepository.findByUsername(securityUtils.getCurrentUserLogin());
        return roomRepository.findByUserId(user.getId()).stream().map(this::mapToRoomDetail).collect(Collectors.toList());
    }*/

   /* @Override
    public RoomDetail getRoom(String roomId) {
        RoomDetail roomDetail = new RoomDetail();
        Room room = roomRepository.findById(roomId).orElseThrow(NoEntityWithSuchId::new);
        return mapToRoomDetail(room);
    }



*/
}

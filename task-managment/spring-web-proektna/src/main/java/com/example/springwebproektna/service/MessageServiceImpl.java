package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.MessageDetails;
import com.example.springwebproektna.exception.NoEntityWithSuchId;
import com.example.springwebproektna.model.Message;
import com.example.springwebproektna.model.Room;
import com.example.springwebproektna.model.User;
import com.example.springwebproektna.repository.MessageRepository;
import com.example.springwebproektna.repository.RoomRepository;
import com.example.springwebproektna.repository.UserRepository;
import com.example.springwebproektna.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private SecurityUtils securityUtils;

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private RoomRepository roomRepository;


    @Autowired
    public MessageServiceImpl(
            SecurityUtils securityUtils,
            MessageRepository messageRepository,
            UserRepository userRepository,
            RoomRepository roomRepository
    ) {
        this.securityUtils = securityUtils;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void handleNewMessage(String roomId, MessageDetails messageDetails) {
        String username = securityUtils.getCurrentUserLogin();
        messageDetails.setUsername(username);
        messageDetails.setDate(new Date());
        Message message = new Message();
        User user = userRepository.findByUsername(username);

        message.setUser(user);
        message.setContent(messageDetails.getContent());
        message.setDate(messageDetails.getDate());

        Optional<Room> room = roomRepository.findById(roomId);
        message.setRoom(room.orElseThrow(NoEntityWithSuchId::new));
        messageRepository.save(message);

    }
}

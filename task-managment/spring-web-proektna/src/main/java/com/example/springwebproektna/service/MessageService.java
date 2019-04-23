package com.example.springwebproektna.service;

import com.example.springwebproektna.domains.MessageDetails;

public interface MessageService {
    void handleNewMessage(String roomId, MessageDetails message);
}

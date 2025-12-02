package com.senchat.service;

import com.senchat.dao.MessageDAO;
import com.senchat.model.Message;

import java.time.LocalDateTime;
import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO = new MessageDAO();

    public Message sendMessage(Message message) {

        if (message == null) {
            System.out.println("Message cannot be null.");
            return null;
        }

        if (message.getUserId() <= 0) {
            System.out.println("Invalid user ID.");
            return null;
        }

        if (message.getRoomId() <= 0) {
            System.out.println("Invalid room ID.");
            return null;
        }

        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            System.out.println("Message content cannot be empty.");
            return null;
        }

        if (message.getSent() == null) {
            message.setSent(LocalDateTime.now());
        }

        return messageDAO.sendMessage(message);
    }

    public List<Message> listMessagesByRoom(int roomId) {

        if (roomId <= 0) {
            System.out.println("Invalid room ID.");
            return null;
        }

        return messageDAO.listMessagesByRoom(roomId);
    }

    public List<Message> listMessagesBetweenUsers(int user1, int user2) {

        if (user1 <= 0 || user2 <= 0) {
            System.out.println("Invalid user IDs.");
            return null;
        }

        if (user1 == user2) {
            System.out.println("Users must be different.");
            return null;
        }

        return messageDAO.listMessagesBetweenUsers(user1, user2);
    }
}

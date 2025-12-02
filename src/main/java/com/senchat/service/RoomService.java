package com.senchat.service;

import com.senchat.dao.RoomDAO;
import com.senchat.model.Room;

import java.util.List;

public class RoomService {

    private final RoomDAO roomDAO = new RoomDAO();

    public void createRoom(Room room) throws Exception {
        if (room.getTopic() == null || room.getTopic().isEmpty()) {
            throw new Exception("Topic cannot be empty.");
        }

        if (room.getSector() == null || room.getSector().isEmpty()) {
            throw new Exception("Sector cannot be empty.");
        }

        if (room.getAdminId() <= 0) {
            throw new Exception("Invalid admin ID.");
        }

        roomDAO.create(room);
    }

    public List<Room> getAllRooms() {
        return roomDAO.findAll();
    }

    public Room getRoomById(int id) {
        return roomDAO.findById(id);
    }

    public void updateRoom(Room room) {
        roomDAO.update(room);
    }

    public void deleteRoom(int id) {
        roomDAO.delete(id);
    }
}

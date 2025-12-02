package com.senchat.service;

import com.senchat.dao.RoomDAO;
import com.senchat.model.Room;

import java.util.List;

public class RoomService {

    private final RoomDAO roomDAO = new RoomDAO();

    public Room createRoom(Room room) {
        if (room == null) {
            System.out.println("Room cannot be null.");
            return null;
        }

        if (room.getTopic() == null || room.getTopic().isEmpty()) {
            System.out.println("Room topic is required.");
            return null;
        }

        if (room.getSector() == null || room.getSector().isEmpty()) {
            System.out.println("Room sector is required.");
            return null;
        }

        if (room.getAdminId() <= 0) {
            System.out.println("Invalid admin ID.");
            return null;
        }

        return roomDAO.createRoom(room);
    }

    public Room getRoomById(int id) {
        if (id <= 0) {
            System.out.println("Invalid room ID.");
            return null;
        }
        return roomDAO.getRoomById(id);
    }

    public List<Room> listRooms() {
        return roomDAO.listRooms();
    }

    public boolean updateRoom(Room room) {
        if (room == null) {
            System.out.println("Room cannot be null.");
            return false;
        }

        if (room.getRoomId() <= 0) {
            System.out.println("Invalid room ID.");
            return false;
        }

        return roomDAO.updateRoom(room);
    }

    public boolean deleteRoom(int roomId) {
        if (roomId <= 0) {
            System.out.println("Invalid room ID.");
            return false;
        }

        return roomDAO.deleteRoom(roomId);
    }
}

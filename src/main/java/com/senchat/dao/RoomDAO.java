package com.senchat.dao;

import com.senchat.model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public Room createRoom(Room room) {
        String sqlCreate = "INSERT INTO rooms (topic, sector, admin_id) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, room.getTopic());
            stmt.setString(2, room.getSector());
            stmt.setInt(3, room.getAdminId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    room.setRoomId(rs.getInt(1));
                }
            }

            return room;

        } catch (SQLException e) {
            System.out.println("Error creating room: " + e.getMessage());
            return null;
        }
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("id"));
                room.setTopic(rs.getString("topic"));
                room.setSector(rs.getString("sector"));
                room.setAdminId(rs.getInt("admin_id"));
                return room;
            }

        } catch (SQLException e) {
            System.out.println("Error fetching room: " + e.getMessage());
        }

        return null;
    }

    public List<Room> listRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("id"));
                room.setTopic(rs.getString("topic"));
                room.setSector(rs.getString("sector"));
                room.setAdminId(rs.getInt("admin_id"));
                rooms.add(room);
            }

        } catch (SQLException e) {
            System.out.println("Error listing rooms: " + e.getMessage());
        }

        return rooms;
    }

    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET topic = ?, sector = ?, admin_id = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, room.getTopic());
            stmt.setString(2, room.getSector());
            stmt.setInt(3, room.getAdminId());
            stmt.setInt(4, room.getRoomId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating room: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRoom(int roomId) {
        String sql = "DELETE FROM rooms WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, roomId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }
}
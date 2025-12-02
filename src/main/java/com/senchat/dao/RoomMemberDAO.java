package com.senchat.dao;

import com.senchat.model.RoomMember;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomMemberDAO {

    public RoomMember addMember(RoomMember member) {
        String sqlAdd = "INSERT INTO room_member (room_id, user_id, role) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlAdd, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1, member.getRoomId());
            pstm.setInt(2, member.getUserId());
            pstm.setString(3, member.getRole());

            int rows = pstm.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    member.setId(rs.getInt(1));
                }
            }

            return member;

        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
            return null;
        }
    }

    public boolean removeMember(int roomId, int userId) {
        String sqlRemove = "DELETE FROM room_member WHERE room_id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlRemove)) {

            pstm.setInt(1, roomId);
            pstm.setInt(2, userId);

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error removing member: " + e.getMessage());
            return false;
        }
    }

    public List<RoomMember> listMembersByRoom(int roomId) {
        List<RoomMember> members = new ArrayList<>();
        String sqlListMembers = "SELECT * FROM room_member WHERE room_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlListMembers)) {

            pstm.setInt(1, roomId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                RoomMember member = new RoomMember();
                member.setId(rs.getInt("member_id"));
                member.setRoomId(rs.getInt("room_id"));
                member.setUserId(rs.getInt("user_id"));
                member.setRole(rs.getString("role"));
                members.add(member);
            }

        } catch (SQLException e) {
            System.out.println("Error listing members: " + e.getMessage());
        }

        return members;
    }

    public String getRoleOfUserInRoom(int roomId, int userId) {
        String sqlSelect = "SELECT role FROM room_member WHERE room_id = ? AND user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlSelect)) {

            pstm.setInt(1, roomId);
            pstm.setInt(2, userId);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (SQLException e) {
            System.out.println("Error getting user role: " + e.getMessage());
        }

        return null;
    }
}
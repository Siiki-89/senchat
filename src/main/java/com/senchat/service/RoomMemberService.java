package com.senchat.service;

import com.senchat.dao.RoomMemberDAO;
import com.senchat.model.RoomMember;

import java.util.List;

public class RoomMemberService {

    private final RoomMemberDAO roomMemberDAO = new RoomMemberDAO();

    public RoomMember addMember(RoomMember member) {

        if (member == null) {
            System.out.println("Member cannot be null.");
            return null;
        }

        if (member.getRoomId() <= 0) {
            System.out.println("Invalid room ID.");
            return null;
        }

        if (member.getUserId() <= 0) {
            System.out.println("Invalid user ID.");
            return null;
        }

        if (member.getRole() == null || member.getRole().trim().isEmpty()) {
            System.out.println("Role cannot be empty.");
            return null;
        }
        RoomMember existingMember = roomMemberDAO.getMemberByRoomAndUser(
                member.getRoomId(),
                member.getUserId()
        );

        if (existingMember != null) {
            return existingMember;
        }

        return roomMemberDAO.addMember(member);
    }

    public boolean removeMember(int roomId, int userId) {

        if (roomId <= 0) {
            System.out.println("Invalid room ID.");
            return false;
        }

        if (userId <= 0) {
            System.out.println("Invalid user ID.");
            return false;
        }

        return roomMemberDAO.removeMember(roomId, userId);
    }

    public List<RoomMember> listMembersByRoom(int roomId) {

        if (roomId <= 0) {
            System.out.println("Invalid room ID.");
            return null;
        }

        return roomMemberDAO.listMembersByRoom(roomId);
    }

}

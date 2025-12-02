package com.senchat.dao;

import com.senchat.model.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    public Message sendMessage(Message message) {
        String sqlInsert = "INSERT INTO messages (user_id, room_id, content, sent) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setInt(1, message.getUserId());
            pstm.setInt(2, message.getRoomId());
            pstm.setString(3, message.getContent());
            pstm.setTimestamp(4, Timestamp.valueOf(message.getSent()));

            int rows = pstm.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pstm.getGeneratedKeys();
                if (rs.next()) {
                    message.setId(rs.getInt(1));
                }
            }

            return message;

        } catch (SQLException e) {
            System.out.println("Error sending messages: " + e.getMessage());
            return null;
        }
    }

    public List<Message> listMessagesByRoom(int roomId) {
        List<Message> messages = new ArrayList<>();
        String sqlList = "SELECT * FROM messages WHERE room_id = ? ORDER BY sent ASC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlList)) {

            pstm.setInt(1, roomId);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setId(rs.getInt("id"));
                msg.setUserId(rs.getInt("user_id"));
                msg.setRoomId(rs.getInt("room_id"));
                msg.setContent(rs.getString("content"));
                msg.setSent(rs.getTimestamp("sent").toLocalDateTime());

                messages.add(msg);
            }

        } catch (SQLException e) {
            System.out.println("Error listing room messages: " + e.getMessage());
        }

        return messages;
    }

    public List<Message> listMessagesBetweenUsers(int user1, int user2) {
        List<Message> messages = new ArrayList<>();

        String sqlListMessage =
                "SELECT * FROM messages " +
                        "WHERE room_id IN ( " +
                        "    SELECT room_id FROM room_member " +
                        "    GROUP BY room_id " +
                        "    HAVING COUNT(room_id) = 2 " +
                        "       AND SUM(CASE WHEN user_id = ? OR user_id = ? THEN 1 ELSE 0 END) = 2 " +
                        ") " +
                        "ORDER BY sent ASC";

        try (Connection connection = com.senchat.dao.DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlListMessage)) {

            pstm.setInt(1, user1);
            pstm.setInt(2, user2);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setId(rs.getInt("id"));
                msg.setUserId(rs.getInt("user_id"));
                msg.setRoomId(rs.getInt("room_id"));
                msg.setContent(rs.getString("content"));
                msg.setSent(rs.getTimestamp("sent").toLocalDateTime());
                messages.add(msg);
            }

        } catch (SQLException e) {
            System.out.println("Error listing private messages: " + e.getMessage());
        }

        return messages;
    }
}

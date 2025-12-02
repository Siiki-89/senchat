package com.senchat.dao;

import com.senchat.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public boolean create(User user) {
        String sqlInsert = "INSERT INTO users (name, nickname, password, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {

            pstm.setString(1, user.getName());
            pstm.setString(2, user.getNickName());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getEmail());

            int rows = pstm.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        }
    }
    public User login(String email, String password) {
        String sqlLogin = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlLogin)) {

            pstm.setString(1, email);
            pstm.setString(2, password);

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null;
    }

    public User findById(int id) {
        String sqlFindId = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlFindId)) {

            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error finding user by ID: " + e.getMessage());
        }
        return null;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sqlFind = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlFind);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error listing users: " + e.getMessage());
        }

        return users;
    }
    public boolean update(User user) {
        String sqlUpdate = "UPDATE users SET name = ?, nickname = ?, password = ?, email = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlUpdate)) {

            pstm.setString(1, user.getName());
            pstm.setString(2, user.getNickName());
            pstm.setString(3, user.getPassword());
            pstm.setString(4, user.getEmail());
            pstm.setInt(5, user.getId());

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sqlDelete = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sqlDelete)) {

            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setNickName(rs.getString("nickname"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }

}

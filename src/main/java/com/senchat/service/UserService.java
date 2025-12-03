package com.senchat.service;

import com.senchat.dao.UserDAO;
import com.senchat.model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) throws Exception {

        user.validar();

        return userDAO.create(user);
    }

    public User login(String email, String password) throws Exception {

        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email cannot be empty.");
        }

        if (password == null || password.isEmpty()) {
            throw new Exception("Password cannot be empty.");
        }

        User user = userDAO.login(email, password);

        if (user == null) {
            throw new Exception("Invalid email or password.");
        }

        return user;
    }

    public String getUserById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Invalid ID.");
        }

        User user = userDAO.findById(id);

        if (user == null) {
            throw new Exception("User not found.");
        }

        return user.getNickName();
    }

    public boolean updateUser(User user) throws Exception {

        if (user.getId() <= 0) {
            throw new Exception("Invalid user ID.");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new Exception("Email cannot be empty.");
        }

        if (!user.getEmail().contains("@")) {
            throw new Exception("Invalid email format.");
        }

        return userDAO.update(user);
    }

    public boolean deleteUser(int id) throws Exception {

        if (id <= 0) {
            throw new Exception("Invalid ID.");
        }

        return userDAO.delete(id);
    }
}

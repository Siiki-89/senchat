package com.senchat.service;

import com.senchat.dao.UserDAO;
import com.senchat.model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) throws Exception {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new Exception("Name cannot be empty.");
        }

        if (user.getNickName() == null || user.getNickName().trim().isEmpty()) {
            throw new Exception("Nickname cannot be empty.");
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new Exception("Email cannot be empty.");
        }

        if (!user.getEmail().contains("@")) {
            throw new Exception("Invalid email format.");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new Exception("Password must have at least 6 characters.");
        }

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

    public List<User> listAllUsers() {
        return userDAO.findAll();
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

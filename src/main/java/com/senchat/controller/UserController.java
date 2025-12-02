package com.senchat.controller;

import com.senchat.model.User;
import com.senchat.service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserController {

    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);
    private User loggedUser;


    public void login() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            loggedUser = userService.login(email, password);

            System.out.println("Login successful. Welcome, " + loggedUser.getNickName() + "!");

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }

    public void registerUser() {
        try {
            User user = new User();

            System.out.print("Name: ");
            user.setName(scanner.nextLine());

            System.out.print("Nickname: ");
            user.setNickName(scanner.nextLine());

            System.out.print("Email: ");
            user.setEmail(scanner.nextLine());

            System.out.print("Password: ");
            user.setPassword(scanner.nextLine());

            boolean created = userService.register(user);
            if (created) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Failed to register user.");
            }

        } catch (Exception e) {
            System.out.println("Register error: " + e.getMessage());
        }
    }

    public void listAllUsers() {
        List<User> users = userService.listAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\n--- Users ---");
        for (User u : users) {
            System.out.println(
                    "ID: " + u.getId() +
                            " | Nickname: " + u.getNickName() +
                            " | Email: " + u.getEmail()
            );
        }
    }

    public void getUserById() {
        try {
            System.out.print("User ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            User user = userService.getUserById(id);
            System.out.println("\nUser found:");
            System.out.println("Name: " + user.getName());
            System.out.println("Nickname: " + user.getNickName());
            System.out.println("Email: " + user.getEmail());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void updateUser() {
        try {
            System.out.print("User ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            User existing = userService.getUserById(id);

            System.out.println("Leave blank to keep current value.");

            System.out.print("New Email: ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                existing.setEmail(email);
            }

            System.out.print("New Password: ");
            String password = scanner.nextLine();
            if (!password.trim().isEmpty()) {
                existing.setPassword(password);
            }

            boolean updated = userService.updateUser(existing);

            if (updated) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("Failed to update user.");
            }

        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    public void deleteUser() {
        try {
            System.out.print("User ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            boolean deleted = userService.deleteUser(id);

            if (deleted) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user.");
            }

        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean isLogged() {
        return loggedUser != null;
    }
}

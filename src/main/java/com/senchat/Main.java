package com.senchat;

import com.senchat.model.User;
import com.senchat.model.Room;
import com.senchat.model.RoomMember;
import com.senchat.model.Message;
import com.senchat.service.UserService;
import com.senchat.service.RoomService;
import com.senchat.service.RoomMemberService;
import com.senchat.service.MessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UserService userService = new UserService();
    private static final RoomService roomService = new RoomService();
    private static final RoomMemberService roomMemberService = new RoomMemberService();
    private static final MessageService messageService = new MessageService();

    private static User loggedUser = null;
    private static Room currentRoom = null;

    public static void main(String[] args) {
        System.out.println("=== SenChat (terminal) ===");

        while (true) {
            if (loggedUser == null) {
                showAuthMenu();
            } else if (currentRoom == null) {
                showMainMenu();
            } else {
                showRoomMenu();
            }
        }
    }

    private static void showAuthMenu() {
        System.out.println("\n--- AUTH ---");
        System.out.println("1 - Login");
        System.out.println("2 - Register");
        System.out.println("0 - Exit");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        switch (opt) {
            case "1": handleLogin(); break;
            case "2": handleRegister(); break;
            case "0": exitApp(); break;
            default: System.out.println("Invalid option.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- MAIN --- (Logado como: " + loggedUser.getPresentation() + ")");

        System.out.println("1 - Create room");
        System.out.println("2 - List rooms");
        System.out.println("3 - Enter room");
        System.out.println("4 - My account");
        System.out.println("5 - Logout");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        switch (opt) {
            case "1": handleCreateRoom(); break;
            case "2": handleListRooms(); break;
            case "3": handleEnterRoom(); break;
            case "4": handleAccountMenu(); break;
            case "5": logout(); break;
            default: System.out.println("Invalid option.");
        }
    }

    private static void showRoomMenu() {
        System.out.println("\n--- ROOM [" + currentRoom.getRoomId() + "] " + currentRoom.getTopic() + " ---");
        System.out.println("1 - Send message");
        System.out.println("2 - Show messages");
        System.out.println("3 - List members");
        System.out.println("4 - Leave room");

        System.out.println("5 - Update room (admin only)");
        System.out.println("6 - Delete room (admin only)");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        switch (opt) {
            case "1": handleSendMessage(); break;
            case "2": handleShowMessages(); break;
            case "3": handleListMembers(); break;
            case "4": leaveRoom(); break;
            case "5": handleUpdateRoom(); break;
            case "6": handleDeleteRoom(); break;
            default: System.out.println("Invalid option.");
        }
    }

    private static void handleLogin() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            loggedUser = userService.login(email, password);

            System.out.println("Login successful. Welcome, " + loggedUser.getPresentation() + "!");

        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void handleRegister() {
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
            System.out.println(created ? "User registered." : "Failed to register.");
        } catch (Exception e) {
            System.out.println("Register failed: " + e.getMessage());
        }
    }

    private static void logout() {
        loggedUser = null;
        currentRoom = null;
        System.out.println("Logged out.");
    }

    private static void exitApp() {
        System.out.println("Bye.");
        System.exit(0);
    }

    private static void handleCreateRoom() {
        try {
            Room room = new Room();
            System.out.print("Topic: ");
            room.setTopic(scanner.nextLine());

            System.out.print("Sector: ");
            room.setSector(scanner.nextLine());

            room.setAdminId(loggedUser.getId());
            Room created = roomService.createRoom(room);

            if (created != null) {
                System.out.println("Room created. ID: " + created.getRoomId());
            } else {
                System.out.println("Failed to create room.");
            }
        } catch (Exception e) {
            System.out.println("Create room error: " + e.getMessage());
        }
    }

    private static void handleListRooms() {
        List<Room> rooms = roomService.listRooms();
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms found.");
            return;
        }
        System.out.println("\n--- Rooms ---");
        for (Room r : rooms) {
            System.out.println("ID: " + r.getRoomId()
                    + " | Topic: " + r.getTopic()
                    + " | Sector: " + r.getSector()
                    + " | Admin: " + r.getAdminId());
        }
    }

    private static void handleEnterRoom() {
        try {
            System.out.print("Room ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Room room = roomService.getRoomById(id);
            if (room == null) {
                System.out.println("Room not found.");
                return;
            }

            RoomMember member = new RoomMember();
            member.setRoomId(room.getRoomId());
            member.setUserId(loggedUser.getId());
            member.setRole("member");

            RoomMember added = roomMemberService.addMember(member);
            if (added == null) {
                System.out.println("Entering room (add member returned null).");
            } else {
                System.out.println("You joined the room.");
            }

            currentRoom = room;
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid ID.");
        }
    }

    private static void leaveRoom() {
        if (currentRoom == null) return;
        boolean removed = roomMemberService.removeMember(currentRoom.getRoomId(), loggedUser.getId());
        if (removed) System.out.println("Left room.");
        else System.out.println("You left the room (or removal failed).");
        currentRoom = null;
    }

    private static void handleUpdateRoom() {
        if (currentRoom == null) return;

        if (currentRoom.getAdminId() != loggedUser.getId()) {
            System.out.println("Only admin can update.");
            return;
        }

        System.out.print("New topic (leave blank to keep): ");
        String topic = scanner.nextLine();
        if (!topic.trim().isEmpty()) currentRoom.setTopic(topic);

        System.out.print("New sector (leave blank to keep): ");
        String sector = scanner.nextLine();
        if (!sector.trim().isEmpty()) currentRoom.setSector(sector);

        boolean ok = roomService.updateRoom(currentRoom);
        System.out.println(ok ? "Room updated." : "Failed to update room.");
    }

    private static void handleDeleteRoom() {
        if (currentRoom == null) return;

        if (currentRoom.getAdminId() != loggedUser.getId()) {
            System.out.println("Only admin can delete.");
            return;
        }

        boolean ok = roomService.deleteRoom(currentRoom.getRoomId());
        System.out.println(ok ? "Room deleted." : "Failed to delete room.");
        if (ok) currentRoom = null;
    }

    private static void handleListMembers() {
        if (currentRoom == null) return;

        List<RoomMember> members = roomMemberService.listMembersByRoom(currentRoom.getRoomId());
        if (members == null || members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        System.out.println("\n--- Members ---");
        for (RoomMember m : members) {
            try {
                String username = userService.getUserById(m.getUserId());
                System.out.println("MemberID: " + m.getId() + " | UserID: " + m.getUserId() + " | " + username);
            } catch (Exception e) {
                System.out.println("Error fetching user name: " + e.getMessage());
            }
        }
    }

    private static void handleSendMessage() {
        if (currentRoom == null) return;

        System.out.print("Message: ");
        String text = scanner.nextLine();
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Message is empty.");
            return;
        }

        Message msg = new Message();
        msg.setUserId(loggedUser.getId());
        msg.setRoomId(currentRoom.getRoomId());
        msg.setContent(text);
        msg.setSent(LocalDateTime.now());

        Message sent = messageService.sendMessage(msg);
        if (sent != null) {
            System.out.println("Message sent. ID: " + sent.getId());
        } else {
            System.out.println("Failed to send message.");
        }
    }

    private static void handleShowMessages() {
        if (currentRoom == null) return;

        List<Message> msgs = messageService.listMessagesByRoom(currentRoom.getRoomId());
        if (msgs == null || msgs.isEmpty()) {
            System.out.println("No messages.");
            return;
        }
        System.out.println("\n--- Messages ---");
        for (Message m : msgs) {
            System.out.println("[" + m.getSent() + "] User " + m.getUserId() + ": " + m.getContent());
        }
    }

    private static void handleAccountMenu() {
        System.out.println("\n--- ACCOUNT ---");
        System.out.println("1 - Show my profile");
        System.out.println("2 - Update password/email");
        System.out.println("3 - Delete account");
        System.out.println("0 - Back");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        switch (opt) {
            case "1": showProfile(); break;
            case "2": updateProfile(); break;
            case "3": deleteAccount(); break;
            case "0": break;
            default: System.out.println("Invalid option.");
        }
    }

    private static void showProfile() {
        System.out.println("\n--- PROFILE ---");
        System.out.println("ID: " + loggedUser.getId());
        System.out.println("Name: " + loggedUser.getName());
        System.out.println("Email: " + loggedUser.getEmail());
        System.out.println("Nickname: " + loggedUser.getNickName());
    }

    private static void updateProfile() {
        try {
            System.out.print("New email (leave blank to keep): ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) loggedUser.setEmail(email);

            System.out.print("New password (leave blank to keep): ");
            String password = scanner.nextLine();
            if (!password.trim().isEmpty()) loggedUser.setPassword(password);

            boolean ok = userService.updateUser(loggedUser);
            System.out.println(ok ? "Profile updated." : "Failed to update profile.");
        } catch (Exception e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }

    private static void deleteAccount() {
        try {
            System.out.print("Type DELETE to confirm: ");
            String confirm = scanner.nextLine();
            if (!"DELETE".equals(confirm)) {
                System.out.println("Aborted.");
                return;
            }
            boolean ok = userService.deleteUser(loggedUser.getId());
            System.out.println(ok ? "Account deleted." : "Failed to delete account.");
            if (ok) {
                loggedUser = null;
                currentRoom = null;
            }
        } catch (Exception e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}
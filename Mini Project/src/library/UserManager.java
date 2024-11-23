package library;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class UserManager {
    private List<User> users;
    private List<Librarian> librarians;

    public UserManager() {
        users = new ArrayList<>();
        librarians = new ArrayList<>();
        loadUsers();
        loadLibrarians();
    }

    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new User(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error loading users: " + e.getMessage());
            LoggerUtil.logSevere("Error loading users: " + e.getMessage());
        }
    }

    private void loadLibrarians() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/librarians.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                librarians.add(new Librarian(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error loading librarians: " + e.getMessage());
            LoggerUtil.logSevere("Error loading librarians: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                LoggerUtil.logInfo(username + " logged in successfully.");
                return user;
            }
        }
        for (Librarian librarian : librarians) {
            if (librarian.getName().equals(username) && librarian.getPassword().equals(password)) {
                LoggerUtil.logInfo(username + " logged in successfully as librarian.");
                return librarian; // Return librarian as user
            }
        }
        LoggerUtil.logWarning("Invalid login attempt for username: " + username);
        return null; // Invalid login
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
        LoggerUtil.logInfo("Added user: " + user.getName());
        JOptionPane.showMessageDialog(null, "User added successfully.");
    }

    public void removeUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
        saveUsers();
        LoggerUtil.logInfo("Removed user with ID: " + id);
        JOptionPane.showMessageDialog(null, "User removed successfully.");
    }

    public void addLibrarian(Librarian librarian) {
        librarians.add(librarian);
        saveLibrarians();
        LoggerUtil.logInfo("Added librarian: " + librarian.getName());
        JOptionPane.showMessageDialog(null, "Librarian added successfully.");
    }

    public void removeLibrarian(String id) {
        librarians.removeIf(librarian -> librarian.getId().equals(id));
        saveLibrarians();
        LoggerUtil.logInfo("Removed librarian with ID: " + id);
        JOptionPane.showMessageDialog(null, "Librarian removed successfully.");
    }

    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/users.txt"))) {
            for (User user : users) {
                bw.write(user.getId() + "," + user.getName() + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error saving users: " + e.getMessage());
            LoggerUtil.logSevere("Error saving users: " + e.getMessage());
        }
    }

    private void saveLibrarians() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/librarians.txt"))) {
            for (Librarian librarian : librarians) {
                bw.write(librarian.getId() + "," + librarian.getName() + "," + librarian.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error saving librarians: " + e.getMessage());
            LoggerUtil.logSevere("Error saving librarians: " + e.getMessage());
        }
    }
}
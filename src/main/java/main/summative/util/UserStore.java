// Updated UserStore.java
package main.summative.util;

import java.util.*;

public class UserStore {

    public static class User {
        private final String email;
        private final String password;
        private final String role; // "admin" or "user"

        public User(String email, String password, String role) {
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getRole() { return role; }
    }

    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, User> pendingUsers = new HashMap<>();

    static {
        addUser("admin@example.com", "admin123", "admin");
        addUser("user@example.com", "userpass", "user");
    }

    public static boolean validate(String email, String password) {
        User user = users.get(email);
        return user != null && user.getPassword().equals(password);
    }

    public static boolean addUser(String email, String password) {
        return addUser(email, password, "user");
    }

    public static boolean addUser(String email, String password, String role) {
        if (users.containsKey(email)) return false;
        users.put(email, new User(email, password, role));
        return true;
    }

    public static boolean registerUser(String email, String password) {
        if (users.containsKey(email) || pendingUsers.containsKey(email)) return false;
        pendingUsers.put(email, new User(email, password, "user"));
        return true;
    }

    public static boolean approveUser(String email) {
        User user = pendingUsers.remove(email);
        if (user != null) {
            users.put(email, user);
            return true;
        }
        return false;
    }

    public static boolean rejectUser(String email) {
        return pendingUsers.remove(email) != null;
    }

    public static boolean removeUser(String email) {
        return users.remove(email) != null;
    }

    public static Map<String, User> getAllUsers() {
        return Collections.unmodifiableMap(users);
    }

    public static Map<String, User> getPendingUsers() {
        return Collections.unmodifiableMap(pendingUsers);
    }

    public static User getUser(String email) {
        return users.get(email);
    }
}

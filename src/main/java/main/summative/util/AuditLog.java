// File: AuditLog.java
package main.summative.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditLog {
    private static final List<String> logs = new ArrayList<>();

    public static void recordLogin(String username, String role) {
        String entry = String.format("%s - %s logged in (%s)", LocalDateTime.now(), username, role);
        logs.add(entry);
    }

    public static List<String> getLogs() {
        return new ArrayList<>(logs);
    }
}

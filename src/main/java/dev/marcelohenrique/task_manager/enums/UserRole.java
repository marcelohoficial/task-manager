package dev.marcelohenrique.task_manager.enums;

public enum UserRole {
    ADMIN,
    MANAGER,
    USER;

    public static UserRole fromString(String role) {
        return UserRole.valueOf(role.toUpperCase());
    }
  
}

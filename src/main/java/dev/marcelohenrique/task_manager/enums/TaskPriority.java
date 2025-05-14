package dev.marcelohenrique.task_manager.enums;

public enum TaskPriority {
  LOW,
  MEDIUM,
  HIGH;

  public static TaskPriority fromString(String priority) {
    return TaskPriority.valueOf(priority.toUpperCase());
  }
}

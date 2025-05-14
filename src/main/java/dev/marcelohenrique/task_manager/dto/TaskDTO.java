package dev.marcelohenrique.task_manager.dto;

import dev.marcelohenrique.task_manager.enums.TaskPriority;
import dev.marcelohenrique.task_manager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDTO {

  private Long id;

  @NotBlank(message = "O título é obrigatório.")
  @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres.")
  private String title;

  @Size(max = 500, message = "A descrição não pode ter mais de 500 caracteres.")
  private String description;

  @NotNull(message = "A prioridade é obrigatória.")
  private TaskPriority priority;

  @NotNull(message = "O status é obrigatório.")
  private TaskStatus status;

  @NotBlank(message = "A data de vencimento é obrigatória.")
  private String deadLine;

  private String completedAt;
  private String createdAt;

  @NotNull(message = "O ID do usuário é obrigatório.")
  private Long userId;

  private String userName;

  public TaskDTO() {  }

  public TaskDTO(Long id, String title, String description, TaskPriority priority, TaskStatus status, String deadLine, String completedAt, String createdAt, Long userId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.priority = priority;
    this.status = status;
    this.userId = userId;
    this.deadLine = deadLine;
    this.completedAt = completedAt;
    this.createdAt = createdAt;
  }
}

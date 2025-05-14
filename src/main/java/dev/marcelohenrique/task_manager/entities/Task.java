package dev.marcelohenrique.task_manager.entities;

import org.hibernate.annotations.CreationTimestamp;

import dev.marcelohenrique.task_manager.dto.TaskDTO;
import dev.marcelohenrique.task_manager.enums.TaskPriority;
import dev.marcelohenrique.task_manager.enums.TaskStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @Size(max = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String deadLine;

    private String completedAt;
    
    @CreationTimestamp
    private String createdAt;

    public Task() {
    }

    public Task(String title, String description, TaskPriority priority, TaskStatus status, String deadLine, Long userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadLine = deadLine;
        this.user.setId(userId);
    }

    public TaskDTO toTaskDTO() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(this.id);
        taskDTO.setTitle(this.title);
        taskDTO.setDescription(this.description);
        taskDTO.setPriority(this.priority);
        taskDTO.setStatus(this.status);
        taskDTO.setUserId(this.user.getId());
        taskDTO.setUserName(this.user.getName());
        taskDTO.setDeadLine(this.deadLine);
        taskDTO.setCompletedAt(this.completedAt);
        taskDTO.setCreatedAt(this.createdAt);

        return taskDTO;
    }
}

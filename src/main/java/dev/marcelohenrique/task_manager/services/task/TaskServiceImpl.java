package dev.marcelohenrique.task_manager.services.task;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.marcelohenrique.task_manager.dto.TaskDTO;
import dev.marcelohenrique.task_manager.dto.UserDTO;
import dev.marcelohenrique.task_manager.entities.Task;
import dev.marcelohenrique.task_manager.entities.User;
import dev.marcelohenrique.task_manager.enums.TaskPriority;
import dev.marcelohenrique.task_manager.enums.TaskStatus;
import dev.marcelohenrique.task_manager.exceptions.ResourceNotFoundException;
import dev.marcelohenrique.task_manager.repositories.TaskRepository;
import dev.marcelohenrique.task_manager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
            .map(User::toUserDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasks() {
        return taskRepository.findAll().stream()
            .map(Task::toTaskDTO)
            .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required to create a task.");
        }

        User user = userRepository.findById(taskDTO.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + taskDTO.getUserId()));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setStatus(TaskStatus.INPROGRESS);
        task.setUser(user);
        task.setDeadLine(taskDTO.getDeadLine());

        Task savedTask = taskRepository.save(task);

        return savedTask.toTaskDTO();
    }

    @Override
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));
        return task.toTaskDTO();
    }

    @Override
    public TaskDTO updateTask(Long id, Map<String, Object> updates) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    task.setTitle((String) value);
                    break;
                case "description":
                    task.setDescription((String) value);
                    break;
                case "priority":
                    task.setPriority(TaskPriority.valueOf((String) value));
                    break;
                case "status":
                    TaskStatus newStatus = TaskStatus.valueOf((String) value);
                    task.setStatus(newStatus);
                    if (newStatus == TaskStatus.COMPLETED) {
                        task.setCompletedAt(java.time.Instant.now().toString());
                    }
                    break;
                case "userId":
                    User user = userRepository.findById(Long.valueOf(value.toString()))
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + value));
                    task.setUser(user);
                    break;
                case "deadLine":
                    task.setDeadLine((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        Task updatedTask = taskRepository.save(task);
        return updatedTask.toTaskDTO();
    }

    @Override
    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }
}

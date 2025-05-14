package dev.marcelohenrique.task_manager.services.task;

import java.util.List;
import java.util.Map;

import dev.marcelohenrique.task_manager.dto.TaskDTO;
import dev.marcelohenrique.task_manager.dto.UserDTO;

public interface TaskService {

    List<UserDTO> getUsers();

    List<TaskDTO> getTasks();

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id, Map<String, Object> updates);

    void deleteTaskById(Long id);
}

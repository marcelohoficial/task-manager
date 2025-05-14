package dev.marcelohenrique.task_manager.dto;

import dev.marcelohenrique.task_manager.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO {
  private Long id;
  private String name;
  private String email;
  private String password;
  private UserRole userRole;
}

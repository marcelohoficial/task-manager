package dev.marcelohenrique.task_manager.dto;

import lombok.Data;

@Data
public class SignupRequest {
  private String name;
  private String email;
  private String password;
}

package dev.marcelohenrique.task_manager.services.auth;

import dev.marcelohenrique.task_manager.dto.SignupRequest;
import dev.marcelohenrique.task_manager.dto.UserDTO;

public interface AuthService {

  UserDTO signupUser(SignupRequest signupRequest);

  boolean existsByEmail(String email);
}

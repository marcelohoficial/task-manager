package dev.marcelohenrique.task_manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.marcelohenrique.task_manager.entities.User;
import dev.marcelohenrique.task_manager.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String username);

  Optional<User> findByUserRole(UserRole admin);  
}

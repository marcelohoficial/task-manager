package dev.marcelohenrique.task_manager.services.auth;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.marcelohenrique.task_manager.dto.SignupRequest;
import dev.marcelohenrique.task_manager.dto.UserDTO;
import dev.marcelohenrique.task_manager.entities.User;
import dev.marcelohenrique.task_manager.enums.UserRole;
import dev.marcelohenrique.task_manager.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createAnAdminAccount() {
        Optional<User> optionalUser = userRepository.findByUserRole(UserRole.ADMIN);

        if (optionalUser.isEmpty()) {
            User adminUser = buildAdminUser();
            userRepository.save(adminUser);
            logger.info("Conta de administrador criada com sucesso: {}", adminUser.getEmail());
        } else {
            logger.info("Conta de administrador já existe.");
        }
    }

    private User buildAdminUser() {
        User user = new User();
        user.setEmail("admin@esig.com.br");
        user.setName("Admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setUserRole(UserRole.ADMIN);
        
        return user;
    }

    @Override
    public UserDTO signupUser(SignupRequest signupRequest) {
        if (signupRequest == null || signupRequest.getEmail() == null || signupRequest.getPassword() == null) {
            throw new IllegalArgumentException("Os dados de cadastro não podem ser nulos.");
        }

        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("O e-mail já está em uso: " + signupRequest.getEmail());
        }


        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.USER);

        User createdUser = userRepository.save(user);

        logger.info("Usuário criado com sucesso: {}", createdUser.getEmail());

        return convertToUserDTO(createdUser);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setUserRole(user.getUserRole());
        return userDTO;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio.");
        }

        return userRepository.findByEmail(email).isPresent();
    }
}

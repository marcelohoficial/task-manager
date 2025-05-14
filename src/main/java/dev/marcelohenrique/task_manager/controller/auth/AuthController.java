package dev.marcelohenrique.task_manager.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.marcelohenrique.task_manager.dto.AuthenticationRequest;
import dev.marcelohenrique.task_manager.dto.AuthenticationResponse;
import dev.marcelohenrique.task_manager.dto.SignupRequest;
import dev.marcelohenrique.task_manager.dto.UserDTO;
import dev.marcelohenrique.task_manager.entities.User;
import dev.marcelohenrique.task_manager.repositories.UserRepository;
import dev.marcelohenrique.task_manager.services.auth.AuthService;
import dev.marcelohenrique.task_manager.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (signupRequest == null || signupRequest.getEmail() == null || signupRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid signup data");
        }

        if (authService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        if (signupRequest.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }

        try {
            UserDTO userDTO = authService.signupUser(signupRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        if (authenticationRequest == null || authenticationRequest.getEmail() == null || authenticationRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password cannot be null");
        }

        try {
            User user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

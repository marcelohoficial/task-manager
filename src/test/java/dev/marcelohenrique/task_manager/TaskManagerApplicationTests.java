package dev.marcelohenrique.task_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import dev.marcelohenrique.task_manager.dto.AuthenticationRequest;
import dev.marcelohenrique.task_manager.dto.SignupRequest;
import dev.marcelohenrique.task_manager.entities.Task;
import dev.marcelohenrique.task_manager.entities.User;
import dev.marcelohenrique.task_manager.enums.UserRole;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskManagerApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

		@Test
    void testCreateUserSuccess() {
        User user = new User();
        user.setEmail("email@test.com");
        user.setName("Teste");
        user.setPassword("123456");
        user.setUserRole(UserRole.USER);
				
				webTestClient
						.post()
						.uri("/api/auth/signup")
						.bodyValue(user)
						.exchange()
						.expectStatus().isCreated();
    }

		@Test
    void testUpdateSignUpFailure() {
        SignupRequest signUp = new SignupRequest();

        webTestClient
            .post()
            .uri("/api/auth/signup")
            .bodyValue(signUp)
            .exchange()
            .expectStatus().isBadRequest();
    }

		@Test
    void testLoginFailure() {
        AuthenticationRequest login = new AuthenticationRequest();

        webTestClient
            .post()
            .uri("/api/auth/login")
            .bodyValue(login)
            .exchange()
            .expectStatus().isBadRequest();
    }

		@Test
    void testCreateTaskFailure() {
        var task = new Task(); 

        webTestClient
            .post()
            .uri("/api/tasks/create")
            .bodyValue(task)
            .exchange()
            .expectStatus().isForbidden();
		}
}


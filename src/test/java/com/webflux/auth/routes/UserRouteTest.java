package com.webflux.auth.routes;

import com.webflux.auth.entity.AppUser;
import com.webflux.auth.handler.dto.AuthDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRouteTest {
    @Autowired
    WebTestClient webClient;

    @Test
    @DisplayName("Should return forbidden status when role is not WRITE")
    @WithMockUser(username = "adam", roles = {"READ"})
    void test_write_api_with_read_role() {
        webClient.post().uri("/write/abdul")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @DisplayName("Sign in should fail without code")
    public void test_login_fail() {
        final String username = "hayk";
        final String password = "fail";

        webClient.post().uri("/auth/login")
                .header(HttpHeaders.ACCEPT, "application/json")
                .bodyValue(new AuthDto.AuthRequest(null, username, password))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Sign-up api should return status 200")
    public void test_signup_success() {
        Random random = new Random();
        final String username = "hayk" + random.nextInt(100);
        final String password = "fail";

        webClient.post().uri("/auth/signup")
                .header(HttpHeaders.ACCEPT, "application/json")
                .bodyValue(new AppUser(username, password))
                .exchange()
                .expectStatus().isOk();
    }
}
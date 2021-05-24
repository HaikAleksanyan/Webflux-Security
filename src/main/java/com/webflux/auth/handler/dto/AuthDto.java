package com.webflux.auth.handler.dto;

import com.webflux.auth.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AuthDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Validated
    public static class AuthRequest {
        private String code;
        @Email(message = "Username should be email")
        @NotNull(message = "Username is required")
        private String username;
        @NotNull(message = "password is required")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthResponse {
        private String token;
    }

    @Data
    @AllArgsConstructor
    public static class SignUpResponse {
        private AppUser data;
        private String secretKey;
    }
}
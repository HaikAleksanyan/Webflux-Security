package com.webflux.auth.handler.dto;

import com.webflux.auth.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthRequest {
        private String code;
        private String username;
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
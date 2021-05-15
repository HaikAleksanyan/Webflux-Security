package com.webflux.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("app_user")
public class AppUser {
    @Id
    private String id;

    private String username;

    private String password;

    private List<AppRole> roles = new ArrayList<>();
}
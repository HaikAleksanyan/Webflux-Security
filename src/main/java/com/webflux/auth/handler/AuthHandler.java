package com.webflux.auth.handler;

import com.webflux.auth.config.security.TokenProvider;
import com.webflux.auth.entity.AppUser;
import com.webflux.auth.handler.dto.AuthDto;
import com.webflux.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@AllArgsConstructor
public class AuthHandler {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final TokenProvider tokenProvider;

    @NonNull
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(AuthDto.AuthRequest.class)
                .flatMap(dto -> repository.findByUsername(dto.getUsername())
                        .flatMap(user -> {
                            if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                                return ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(new AuthDto.AuthResponse(tokenProvider.generateToken(user)));
                            } else {
                                return ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue("Wrong credentials");
                            }
                        }).switchIfEmpty(ServerResponse.notFound().build()));
    }

    @NonNull
    public Mono<ServerResponse> signUp(ServerRequest request) {
        return request.bodyToMono(AppUser.class)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setRoles(Collections.emptyList());
                    return user;
                }).flatMap(user -> repository.findByUsername(user.getUsername())
                        .flatMap(existing -> ServerResponse.badRequest().contentType(MediaType.APPLICATION_JSON).bodyValue("Username already exists!"))
                        .switchIfEmpty(repository.save(user)
                                .flatMap(savedUser -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(savedUser))));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_WRITE')")
    public Mono<ServerResponse> write(ServerRequest request) {
        return ServerResponse.ok().bodyValue(request.pathVariable("name"));
    }
}
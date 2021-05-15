package com.webflux.auth.repository;

import com.webflux.auth.entity.AppUser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<AppUser, String> {

    Mono<AppUser> findByUsername(String username);
}
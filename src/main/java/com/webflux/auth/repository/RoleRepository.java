package com.webflux.auth.repository;

import com.webflux.auth.entity.AppRole;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<AppRole, String> {

    Mono<AppRole> findByName(String name);
}
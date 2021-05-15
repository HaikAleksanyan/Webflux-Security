package com.webflux.auth.routes;

import com.webflux.auth.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRoutes {

    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthHandler authHandler) {
        return nest(path("/auth"),
                nest(accept(APPLICATION_JSON),
                        route(POST("/login"), authHandler::login).
                                andRoute(POST("/signup"), authHandler::signUp)));
    }

    @Bean
    public RouterFunction<ServerResponse> write(AuthHandler authHandler) {
        return route(POST("/write/{name}").and(accept(APPLICATION_JSON)), authHandler::write);
    }
}
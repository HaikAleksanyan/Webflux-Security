package com.webflux.auth.handler;

import com.webflux.auth.handler.dto.AuthDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.Validator;
import java.util.function.Function;

@Component
public class RequestHandler {

    private final Validator validator;

    public RequestHandler(Validator validator) {
        this.validator = validator;
    }

    public <BODY> Mono<ServerResponse> requireValidBody(Function<Mono<BODY>, Mono<ServerResponse>> block,
                                                        ServerRequest request, Class<BODY> bodyClass) {
        return request
                .bodyToMono(bodyClass)
                .flatMap(body -> validator.validate(body).isEmpty()
                        ? block.apply(Mono.just(body))
                        : ServerResponse.unprocessableEntity().build()
                );
    }

    public <T> Mono<T> requireValid(T obj) {
        return Mono.just(obj)
                .map(dto -> {
                            if (!validator.validate(dto).isEmpty()) {
                                throw new RuntimeException("not valid");
                            }
                            return dto;
                        }
                );
    }
}
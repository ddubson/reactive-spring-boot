package com.ddubson.webflux.web

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.BodyInserter
import reactor.core.publisher.Mono

@Component
class HelloWorldHandler {
    fun hello(request: ServerRequest): Mono<ServerResponse> = ok()
            .contentType(APPLICATION_JSON_UTF8)
            .body()
}
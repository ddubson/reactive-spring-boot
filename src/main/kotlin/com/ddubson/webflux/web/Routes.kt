package com.ddubson.webflux.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import org.springframework.http.MediaType.*

@Configuration
class Routes(private val helloWorldHandler: HelloWorldHandler) {
    @Bean
    fun routes() = router {
        (accept(APPLICATION_JSON) and "/api").nest {
            "/hello-world".nest {
                GET("/", helloWorldHandler::hello)
            }
        }
    }
}
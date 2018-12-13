package reactive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@Configuration
class RouteConfig {
    @Bean
    fun routes() = router {
        GET("/hi") {
            ServerResponse.ok().body(Flux.just("Hello, world!"), String::class.java)
        }
    }
}
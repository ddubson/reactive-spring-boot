package reactive.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@Configuration
class FunctionalKotlinConfig {
    @Bean
    fun routes() = router {
        GET("/frp/kotlin/hi") {
            ServerResponse.ok().body(Flux.just("Hello"), String::class.java)
        }
    }
}
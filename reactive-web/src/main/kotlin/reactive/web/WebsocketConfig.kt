package reactive.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant

@Configuration
class WebsocketConfig {
    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter = WebSocketHandlerAdapter()

    @Bean
    fun webSocketHandler() = WebSocketHandler { session ->
        val messageFlux = Flux
                .generate<Greeting> {
                    it.next(Greeting("Hello @ ${Instant.now()}"))
                }
                .map {
                    session.textMessage(it.text)
                }
                .doFinally {
                    println("Goodbye.")
                }
                .delayElements(Duration.ofSeconds(1))
        session.send(messageFlux)
    }

    @Bean
    fun handlerMapping(): HandlerMapping {
        val simpleUrlHandlerMapping = SimpleUrlHandlerMapping()
        simpleUrlHandlerMapping.urlMap = mapOf(Pair("/ws/hello", webSocketHandler()))
        simpleUrlHandlerMapping.order = 10
        return simpleUrlHandlerMapping
    }
}
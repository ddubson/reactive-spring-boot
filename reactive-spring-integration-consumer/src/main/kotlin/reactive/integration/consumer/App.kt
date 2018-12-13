package reactive.integration.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime

@SpringBootApplication
class ConsumerApp {
    @Bean
    fun queue(): QueueChannel = QueueChannel()

    @Bean
    fun integrationFlow(queue: QueueChannel): IntegrationFlow {
        val dateFlux: Flux<Message<*>> = Flux.generate<LocalDateTime> { it.next(LocalDateTime.now()) }
                .delayElements(Duration.ofSeconds(1))
                .map { MessageBuilder.withPayload(it).build() }

        return IntegrationFlows.from(dateFlux)
                .handle { date: LocalDateTime, headers: Map<String, Any> ->
                    println("Headers: $headers")
                    println("Payload: $date")
                }
                .channel(queue)
                .get()
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumerApp>(*args)
}
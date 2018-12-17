package reactive.integration.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.context.annotation.Bean
import org.springframework.integration.channel.QueueChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.LocalDateTime

@SpringBootApplication
@EnableBinding(Sink::class)
class ConsumerApp {
    @StreamListener
    fun process(@Input(Sink.INPUT) incomingStrings: Flux<String>) {
        incomingStrings.map {
            it.toUpperCase()
        }.subscribe {
            println(it)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumerApp>(*args)
}

/**
 * This ConsumerChannels interface is akin to Sink.class that comes with Spring Cloud Messaging library
 * -- displayed here for reference only --
 */
interface ConsumerChannels {
    companion object {
        const val INPUT_CHANNEL_NAME: String = "input"
    }

    @Input(INPUT_CHANNEL_NAME)
    fun input(): SubscribableChannel
}
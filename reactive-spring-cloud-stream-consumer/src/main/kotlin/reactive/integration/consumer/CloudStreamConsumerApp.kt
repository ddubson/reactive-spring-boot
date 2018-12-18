package reactive.integration.consumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.SubscribableChannel
import reactor.core.publisher.Flux

@SpringBootApplication
@EnableBinding(Sink::class)
class CloudStreamConsumerApp {
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
    runApplication<CloudStreamConsumerApp>(*args)
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
package reactive.integration.consumer

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Bean
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder

@SpringBootApplication
@EnableBinding(Source::class)
class CloudStreamProducerApp {
    private val log: Log = LogFactory.getLog(javaClass)

    @Bean
    fun producer(source: Source): ApplicationRunner {
        return ApplicationRunner {
            (1..10).forEach {
                val message = MessageBuilder.withPayload("Greetings #$it").build()
                this.log.info("Sending message $message")
                source.output().send(message)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<CloudStreamProducerApp>(*args)
}
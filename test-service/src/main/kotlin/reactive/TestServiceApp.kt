package reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant

@SpringBootApplication
class TestServiceApp

fun main(args: Array<String>) {
    runApplication<TestServiceApp>(*args)
}

@Service
class PublisherService {
    // Generate an unbounded amount of data every 1 seconds
    fun publish(): Flux<String> {
        return Flux.generate<String> {
            it.next("Hello @ ${Instant.now()}")
        }.delayElements(Duration.ofSeconds(1))
    }
}
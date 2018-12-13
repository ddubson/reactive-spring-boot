package reactive

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.time.Duration
import java.time.Instant

@Service
class PublisherService {
    // Generate an unbounded amount of data every 1 seconds
    fun publish(): Flux<String> {
        return Flux.generate<String> {
            it.next("Hello @ ${Instant.now()}")
        }.delayElements(Duration.ofSeconds(1))
    }
}
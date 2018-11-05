package reactive.web

import org.reactivestreams.Publisher
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Instant

@RestController
class GreetingsRestController {
    @GetMapping("/greetings")
    fun greetingPublisher(): Publisher<Greeting> {
        return Mono.just(Greeting("Hello, world!"))
    }

    @GetMapping("/lotsofgreetings")
    fun greetingsALot(): Publisher<Greeting> {
        return Flux.generate<Greeting> {
            it.next(Greeting("Hello!"))
        }.take(1000)
    }

    @GetMapping("/sse", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun sseGreetings(): Publisher<Greeting> {
        return Flux.generate<Greeting> {
            it.next(Greeting("Hello, World! @ ${Instant.now()}"))
        }.delayElements(Duration.ofSeconds(1))
    }
}


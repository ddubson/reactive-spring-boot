package reactive.web

import org.reactivestreams.Publisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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
}


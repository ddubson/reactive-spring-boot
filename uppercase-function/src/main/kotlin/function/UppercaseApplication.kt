package function

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import reactor.core.publisher.Flux

@SpringBootApplication
class UppercaseApplication {
    @Bean
    fun uppercase(): (Flux<Input>) -> Flux<Output> {
        return { incoming ->
            incoming.map {
                Output(it.value.toUpperCase())
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<UppercaseApplication>(*args)
}

data class Input(val value: String)

data class Output(val value: String)

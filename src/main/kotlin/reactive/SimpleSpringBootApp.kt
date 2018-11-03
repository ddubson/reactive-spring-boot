package reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleSpringBootApp

fun main(args: Array<String>) {
   runApplication<SimpleSpringBootApp>(*args)
}
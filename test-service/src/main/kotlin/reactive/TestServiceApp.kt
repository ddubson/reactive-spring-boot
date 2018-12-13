package reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestServiceApp

fun main(args: Array<String>) {
    runApplication<TestServiceApp>(*args)
}


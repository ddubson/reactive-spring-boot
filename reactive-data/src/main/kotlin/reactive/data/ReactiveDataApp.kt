package reactive.data

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@SpringBootApplication
class ReactiveDataApp

fun main(args: Array<String>) {
    runApplication<ReactiveDataApp>(*args)

    Thread.sleep(1000 * 10)
}

@Component
class DataInitializer(val reservationRepository: ReservationRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val names: Flux<String> = Flux.just("Pete", "Julie", "Josh", "Todd")
        val reservationFlux = names.map {
            Reservation(null, it)
        }
        val map: Flux<Reservation> = reservationFlux.flatMap {
            this.reservationRepository.save(it)
        }

        // Subscribe to the publisher (flux) -- at which point the saves will be invoked
        map.doOnSubscribe { subscription ->
            println("Subscribed!")
            subscription.request(10)
        }
        map.doOnError {
            println("errored. $it")
        }
        map.doOnComplete {
            println("Done!")
        }
        map.subscribe {
            println("new reservation. $it")
        }
    }
}

@Document
data class Reservation(@Id val id: String?,
                       val reservationName: String)

interface ReservationRepository : ReactiveMongoRepository<Reservation, String> {
    fun findByReservationName(rn: String): Flux<Reservation>
}

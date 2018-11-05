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
}

@Component
class DataInitializer(val reservationRepository: ReservationRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        this.reservationRepository
                .deleteAll()
                .thenMany(Flux.just("Pete", "Julie", "Josh", "Todd").map {
                    Reservation(null, it)
                }.flatMap {
                    this.reservationRepository.save(it)
                })
                .thenMany(this.reservationRepository.findAll())
                .subscribe { sub -> println(sub) }
    }
}

@Document
data class Reservation(@Id val id: String?,
                       val reservationName: String)

interface ReservationRepository : ReactiveMongoRepository<Reservation, String> {
    fun findByReservationName(rn: String): Flux<Reservation>
}

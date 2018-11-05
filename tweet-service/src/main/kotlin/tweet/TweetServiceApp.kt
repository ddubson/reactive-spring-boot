package tweet

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.javadsl.AsPublisher
import akka.stream.javadsl.Sink
import akka.stream.javadsl.Source
import org.reactivestreams.Publisher
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class TweetServiceApp {
    @Bean
    fun applicationRunner(): ApplicationRunner {
        return ApplicationRunner { args ->
        }
    }

    @Bean
    fun routes(tweetService: TweetService): RouterFunction<ServerResponse> = router {
        GET("/tweets") { request ->
            ok().body(tweetService.getAllTweets())
        }

        GET("/hashtags") { request ->
            ok().body(tweetService.getAllHashTags())
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TweetServiceApp>(*args)
}

@Document
data class HashTag(@Id val id: String)

@Document
data class Tweet(@Id val id: String,
                 val text: String,
                 val author: Author) {
    fun getHashTags(): Set<HashTag> = this.text.split(" ")
            .filter { token: String -> token.startsWith("#") }
            .map { word: String -> HashTag(word.replace("[^#\\w+]", "")) }
            .toSet()
}

@Document
data class Author(@Id val handle: String)

@Service
class TweetService(val tweetRepository: TweetRepository,
                   val actorMaterializer: ActorMaterializer) {
    fun getAllTweets(): Publisher<Tweet> = tweetRepository.findAll()

    fun getAllHashTags(): Publisher<HashTag> = Source
            .fromPublisher(getAllTweets())
            .map { it.getHashTags() }
            .reduce { inputA, inputB -> this.join(inputA, inputB) }
            .mapConcat { tags: Set<HashTag> -> tags }
            .runWith(Sink.asPublisher(AsPublisher.WITH_FANOUT), this.actorMaterializer)

    private fun <T> join(inputA: Set<T>, inputB: Set<T>): Set<T> {
        val mergedSet: Set<T> = HashSet()
        mergedSet.plus(inputA)
        mergedSet.plus(inputB)
        return mergedSet
    }
}

interface TweetRepository : ReactiveMongoRepository<Tweet, String>

@Configuration
class AkkaConfiguration {
    @Bean
    fun actorSystem(): ActorSystem = ActorSystem.create("tweet-actor-system")

    @Bean
    fun actorMaterializer(): ActorMaterializer = ActorMaterializer.create(actorSystem())
}
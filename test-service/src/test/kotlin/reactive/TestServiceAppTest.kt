package reactive

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import java.time.Duration

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestServiceAppTest {
    @LocalServerPort
    var port = 0

    @Autowired
    lateinit var applicationContext: ApplicationContext

    lateinit var webTestClient: WebTestClient
    private val publisherService = PublisherService()

    @Before
    fun setup() {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .baseUrl("http://localhost:$port")
                .build()
    }

    @Test
    fun getGreetingTest() {
        this.webTestClient.get().uri("/hi").exchange().expectStatus().isOk
    }

    @Test
    fun publishEndlesslyWithDelayTest() {
        // Use StepVerifier to simulate time discreteness
        // Publish, then take 10, collect to list, then simulate passage of 10 hours
        // then assert that the list has 10 items
        // end with verify complete
        StepVerifier.withVirtualTime {
            this.publisherService.publish().take(10).collectList()
        }.thenAwait(Duration.ofHours(10))
                .consumeNextWith { assertEquals(10, it.size) }
                .verifyComplete()
    }
}
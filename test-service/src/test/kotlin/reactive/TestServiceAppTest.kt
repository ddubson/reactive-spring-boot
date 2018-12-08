package reactive

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.test.StepVerifier
import java.time.Duration

@RunWith(SpringRunner::class)
@SpringBootTest
class TestServiceAppTest {
    private val publisherService = PublisherService()

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
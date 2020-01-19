package by.next.way.rabbitmq

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RabbitMQSimpleClientTest {

    private val rabbitMQSimpleClient = RabbitMQSimpleClient(
            exchange = "test-exchange",
            queue = "test-queue",
            key = "test-key",
            userName = "user",
            password = "password"
    )

    @Test
    fun test() {
        rabbitMQSimpleClient.read()
        val result = rabbitMQSimpleClient.send(
                data = TestData(
                        text = "Hello world!"
                )
        )
        Assertions.assertTrue(result)
    }
}
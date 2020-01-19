package by.next.way.rabbitmq

import org.junit.jupiter.api.Test

class RabbitMQSenderTest {

    private val rabbitMQSender = RabbitMQSender(
            exchange = "test-exchange",
            userName = "user",
            password = "password"
    )

    @Test
    fun test() {
        val result = rabbitMQSender.send(
                key = "TEST-key",
                data = TestData(
                        text = "Hello world!"
                )
        )
    }
}
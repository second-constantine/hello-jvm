package by.next.way.feign.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class FeignConnectorTest {

    private val fiegnConnector = FeignConnector.connect("https://postman-echo.com")

    @Test
    fun post() {
        val response = fiegnConnector.post("Hello world!")
        Assertions.assertTrue(response.contains("Hello world!"))
    }
}
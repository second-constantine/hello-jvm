package by.next.way.feign.kotlin

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class FeignConnectorTest {

    private val fiegnConnector = FeignConnector.connect("https://postman-echo.com")

    @Test
    fun post() {
        val response = fiegnConnector.post("Hello world!")
        log.info(response)
        Assertions.assertTrue(response.contains("Hello world!"))
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}
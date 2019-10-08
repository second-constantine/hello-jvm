package by.next.way.http.client.kotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class HttpServiceTest {

    private val httpService = HttpService()

    @Test
    fun get() {
        val response = httpService.sendGet("https://next-way.by/")
        Assertions.assertTrue(response.contains("second-constantine"))
    }

    @Test
    fun post() {
        val response = httpService.sendPost("https://postman-echo.com/post", "hello world!")
        Assertions.assertTrue(response.contains("hello world!"))
    }
}
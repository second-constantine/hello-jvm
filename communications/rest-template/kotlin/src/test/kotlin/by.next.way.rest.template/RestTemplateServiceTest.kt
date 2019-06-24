package by.next.way.rest.template

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

@Disabled
class RestTemplateServiceTest {

    private val restTemlate = RestTemplate()

    @Test
    fun post() {
        val request = HttpEntity("Hello world!")
        val response = restTemlate.postForObject("https://postman-echo.com/post", request, String::class.java)
        Assertions.assertTrue(response.contains("Hello world!"))
    }


    @Test
    fun get() {
        val response = restTemlate.getForObject("https://next-way.by/", String::class.java)
        Assertions.assertTrue(response.contains("second-constantine"))
    }
}
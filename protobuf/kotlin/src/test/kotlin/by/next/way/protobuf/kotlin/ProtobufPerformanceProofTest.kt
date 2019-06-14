package by.next.way.protobuf.kotlin

import by.next.way.protobuf.kotlin.model.Person
import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProtobufPerformanceProofTest {

    @LocalServerPort
    private val port: Int = 0

    private val restTemplate = TestRestTemplate()
    private val gson = Gson()


    @Test
    fun testProtoBuf() {
        val response = RestTemplate(arrayListOf(ProtobufHttpMessageConverter()) as List<HttpMessageConverter<*>>)
                .getForEntity(createURLWithPort("/proto_buf", port), PersonProto::class.java)
        Assert.assertNotNull(response.body)
    }

    @Test
    fun testJson() {
        val response = RestTemplate().getForEntity(createURLWithPort("/json", port), String::class.java)
        val person = gson.fromJson(response.body, Person::class.java)
        Assert.assertNotNull(person)
    }

    companion object {

        private fun createURLWithPort(uri: String, port: Int): String {
            return "http://localhost:$port$uri"

        }
    }
}
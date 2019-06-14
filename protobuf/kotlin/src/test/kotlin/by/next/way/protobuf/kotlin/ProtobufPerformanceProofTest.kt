package by.next.way.protobuf.kotlin

import by.next.way.protobuf.kotlin.model.Person
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
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

    @Test
    fun testProtoBuf() {
        val response = RestTemplate(arrayListOf(ProtobufHttpMessageConverter()) as List<HttpMessageConverter<*>>)
                .getForEntity(createURLWithPort("/proto_buf", port), PersonProto::class.java)
        Assert.assertNotNull(response.body)
    }

    @Test
    fun testJson() {
        val response = RestTemplate().getForEntity(createURLWithPort("/json", port), Person::class.java)
        val person = response.body
        Assert.assertNotNull(person)
    }

    companion object {

        private fun createURLWithPort(uri: String, port: Int): String {
            return "http://localhost:$port$uri"

        }
    }
}
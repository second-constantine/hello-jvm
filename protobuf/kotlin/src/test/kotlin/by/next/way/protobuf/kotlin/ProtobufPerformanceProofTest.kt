package by.next.way.protobuf.kotlin

import by.next.way.protobuf.kotlin.model.Person
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.logging.log4j.LogManager
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProtobufPerformanceProofTest {

    private val client: CloseableHttpClient = HttpClientBuilder.create().build()
    @LocalServerPort
    private val port: Int = 0

    @Test
    fun aTimeForStart() {
        log.info("Start")
    }

    @Test
    fun testProtoBuf() {
        val response = RestTemplate(arrayListOf(ProtobufHttpMessageConverter()) as List<HttpMessageConverter<*>>)
                .getForEntity(createURLWithPort("/proto_buf", port), PersonProto::class.java)
        Assert.assertNotNull(response.body)
    }

    @Test
    fun testProtoBuf2() {
        val request = HttpGet(createURLWithPort("/proto_buf", port))
        val response = client.execute(request)
        val person = PersonProto.parseFrom(response.entity.content)
        Assert.assertNotNull(person)
    }

    @Test
    fun testJson() {
        val response = RestTemplate().getForEntity(createURLWithPort("/json", port), Person::class.java)
        val person = response.body
        Assert.assertNotNull(person)
    }

    companion object {

        private val log = LogManager.getLogger()
        private fun createURLWithPort(uri: String, port: Int): String {
            return "http://localhost:$port$uri"

        }
    }
}
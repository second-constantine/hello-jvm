package by.next.way.aws.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AppTest {

    @Test
    fun successfulResponse() {
        val app = AwsLambda()
        val result = app.handleRequest(null, null) as GatewayResponse
        assertEquals(result.statusCode, 200)
        assertEquals(result.headers.get("Content-Type"), "application/json")
        val content = result.body
        assertNotNull(content)
        assertTrue(content.contains("\"message\""))
        assertTrue(content.contains("\"hello world\""))
        assertTrue(content.contains("\"location\""))
    }
}
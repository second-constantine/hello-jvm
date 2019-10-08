package by.next.way.hystrix.kotlin

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CommandHelloWorldTest {

    @Test
    fun givenInputBobAndDefaultSettings_whenCommandExecuted_thenReturnHelloBob() {
        val result = CommandHelloWorld("Bob").execute()
        log.info(result)
        Assertions.assertEquals(result, "Hello Bob!")
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}
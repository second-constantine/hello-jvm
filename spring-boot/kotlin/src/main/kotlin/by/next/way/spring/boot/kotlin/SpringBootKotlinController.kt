package by.next.way.spring.boot.kotlin

import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SpringBootKotlinController {

    @GetMapping
    fun hello(): String {
        log.info(HELLO_WORLD)
        log.warn(HELLO_WORLD)
        log.error(HELLO_WORLD)
        return HELLO_WORLD
    }

    companion object {
        const val HELLO_WORLD = "Hello world!"
        private val log = LogManager.getLogger()
    }
}
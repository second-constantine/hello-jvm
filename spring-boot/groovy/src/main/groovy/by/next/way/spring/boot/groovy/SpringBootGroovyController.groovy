package by.next.way.spring.boot.groovy

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SpringBootGroovyController {

    private static String HELLO_WORLD = "Hello world!"
    private Logger log = LogManager.getLogger()

    @GetMapping
    String hello() {
        log.info(HELLO_WORLD)
        log.warn(HELLO_WORLD)
        log.error(HELLO_WORLD)
        return HELLO_WORLD
    }
}

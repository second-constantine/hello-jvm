package by.next.way.spring.boot.webflux.config

import by.next.way.spring.boot.webflux.handler.IntegerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Handler {

    @Bean
    fun integerHandler() = IntegerHandler()

}
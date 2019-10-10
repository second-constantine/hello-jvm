package by.next.way.spring.boot.webflux.config

import by.next.way.spring.boot.webflux.handler.IntegerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class Routing {

    @Bean
    fun integerEndpointRouting(integerHandler: IntegerHandler): RouterFunction<ServerResponse> {
        return route(GET("/api/integer"), HandlerFunction<ServerResponse> { integerHandler.getInteger(it) })
    }

}
package by.next.way.spring.boot.webflux.handler

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

class IntegerHandler {

    fun getInteger(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(IntegerResponse(1)), IntegerResponse::class.java)
    }

}
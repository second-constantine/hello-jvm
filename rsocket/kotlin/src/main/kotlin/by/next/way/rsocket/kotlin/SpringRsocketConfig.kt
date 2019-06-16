package by.next.way.rsocket.kotlin

import io.rsocket.AbstractRSocket
import io.rsocket.Payload
import io.rsocket.RSocketFactory
import io.rsocket.transport.netty.server.TcpServerTransport
import io.rsocket.util.DefaultPayload
import io.rsocket.util.RSocketProxy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@SpringBootApplication
@ComponentScan("by.next.way.rsocket.kotlin")
class SpringRsocketConfig {

    @Bean
    fun rsocket() = RSocketFactory.receive()
            .acceptor { _, _ ->
                Mono.just(RSocketProxy(object : AbstractRSocket() {
                    override fun requestStream(payload: Payload): Flux<Payload> {
                        return Flux.just(DefaultPayload.create("Hello, ${payload.dataUtf8}!!!", "METADATA"))
                    }
                }))
            }
            .transport(TcpServerTransport.create(8081))
            .start()
            .block()

}
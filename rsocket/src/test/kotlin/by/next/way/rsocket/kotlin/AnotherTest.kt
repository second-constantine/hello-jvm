package by.next.way.rsocket.kotlin

import io.rsocket.AbstractRSocket
import io.rsocket.Payload
import io.rsocket.RSocket
import io.rsocket.RSocketFactory
import io.rsocket.transport.netty.client.TcpClientTransport
import io.rsocket.transport.netty.server.CloseableChannel
import io.rsocket.transport.netty.server.TcpServerTransport
import io.rsocket.util.DefaultPayload
import io.rsocket.util.EmptyPayload
import io.rsocket.util.RSocketProxy
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class AnotherTest {

    private var handler: AbstractRSocket? = null

    private var server: CloseableChannel? = null

    @Before
    fun startup() {
        val serverTransport = TcpServerTransport.create(0)
        server = RSocketFactory.receive()
                .acceptor { _, _ -> Mono.just(RSocketProxy(handler!!)) }
                .transport(serverTransport)
                .start()
                .block()
    }

    private fun buildClient(): RSocket {
        return RSocketFactory.connect()
                .transport(TcpClientTransport.create(server!!.address()))
                .start()
                .block()!!
    }

    @After
    fun cleanup() {
        server!!.dispose()
    }

    @Test(timeout = 15_000L)
    fun testCompleteWithoutNext() {
        handler = object : AbstractRSocket() {
            override fun requestStream(payload: Payload): Flux<Payload> {
                return Flux.empty()
            }
        }
        val client = buildClient()
        val hasElements = client.requestStream(DefaultPayload.create("REQUEST", "META")).log().hasElements().block()

        Assert.assertFalse(hasElements!!)
    }

    @Test(timeout = 15_000L)
    fun testSingleStream() {
        handler = object : AbstractRSocket() {
            override fun requestStream(payload: Payload): Flux<Payload> {
                return Flux.just(DefaultPayload.create("RESPONSE", "METADATA"))
            }
        }

        val client = buildClient()

        val result = client.requestStream(DefaultPayload.create("REQUEST", "META")).blockLast()

        assertEquals("RESPONSE", result?.dataUtf8)
    }

    @Test(timeout = 15_000L)
    fun testZeroPayload() {
        handler = object : AbstractRSocket() {
            override fun requestStream(payload: Payload): Flux<Payload> {
                return Flux.just(EmptyPayload.INSTANCE)
            }
        }

        val client = buildClient()

        val result = client.requestStream(DefaultPayload.create("REQUEST", "META")).blockFirst()

        assertEquals("", result?.dataUtf8)
    }

    @Test(timeout = 15_000L)
    fun testRequestResponseErrors() {
        handler = object : AbstractRSocket() {
            internal var first = true

            override fun requestResponse(payload: Payload): Mono<Payload> {
                return if (first) {
                    first = false
                    Mono.error(RuntimeException("EX"))
                } else {
                    Mono.just(DefaultPayload.create("SUCCESS"))
                }
            }
        }

        val client = buildClient()

        val response1 = client
                .requestResponse(DefaultPayload.create("REQUEST", "META"))
                .onErrorReturn(DefaultPayload.create("ERROR"))
                .block()
        val response2 = client
                .requestResponse(DefaultPayload.create("REQUEST", "META"))
                .onErrorReturn(DefaultPayload.create("ERROR"))
                .block()

        assertEquals("ERROR", response1?.dataUtf8)
        assertEquals("SUCCESS", response2?.dataUtf8)
    }
}
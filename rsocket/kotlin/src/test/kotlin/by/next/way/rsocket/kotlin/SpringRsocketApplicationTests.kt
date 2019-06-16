package by.next.way.rsocket.kotlin

import io.rsocket.RSocketFactory
import io.rsocket.transport.netty.client.TcpClientTransport
import io.rsocket.util.DefaultPayload
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
class SpringRsocketApplicationTests {


    @Test
    fun testRsocket() {
        val client = RSocketFactory.connect()
                .transport(TcpClientTransport.create(8081))
                .start()
                .block()!!
        val result = client.requestStream(DefaultPayload.create("Rsocket", "META")).blockLast()
        Assert.assertEquals("Hello, Rsocket!!!", result?.dataUtf8)
    }

}

package by.next.way.rabbitmq

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Address
import com.rabbitmq.client.ConnectionFactory
import org.apache.logging.log4j.LogManager
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.rabbitmq.OutboundMessage
import reactor.rabbitmq.RabbitFlux
import reactor.rabbitmq.Sender
import reactor.rabbitmq.SenderOptions

class RabbitMQSender(
        private val exchange: String,
        private val userName: String = "guest",
        private val password: String = "guest",
        private val host: String = "localhost",
        private val sender: Sender = initSender(
                host = host,
                exchange = exchange,
                userName = userName,
                password = password
        ),
        private val gson: Gson = Gson()
) {

    fun send(key: String, data: Any): Disposable {
        val properties = AMQP.BasicProperties.Builder()
                .type(key)
                .contentType("application/json")
                .deliveryMode(2)
                .build()
        val json = gson.toJson(data)
        log.info("Send to $key: $json")

        val outboundFlux = Flux.just(OutboundMessage(
                exchange,
                key,
                properties,
                json.toByteArray()
        ))
        return sender.send(outboundFlux)
                .doOnError { e: Throwable -> log.error("Send failed", e) }
                .subscribe()
    }


    companion object {
        private val log = LogManager.getLogger()

        fun initSender(host: String, exchange: String, userName: String, password: String): Sender {
            val factory = ConnectionFactory()
            factory.username = userName
            factory.password = password
            factory.useNio()
            val senderOptions = SenderOptions()
                    .connectionFactory(factory)
                    .connectionSupplier { cf: ConnectionFactory ->
                        cf.newConnection(arrayOf(Address(host)), "reactive-sender")
                    }
                    .resourceManagementScheduler(Schedulers.elastic())
            factory.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    channel.exchangeDeclare(exchange, "fanout")
                    val message = "info: Hello World!"
                    channel.basicPublish(exchange, "", null, message.toByteArray(charset("UTF-8")))
                    println(" [x] Sent '$message'")
                }
            }
            return RabbitFlux.createSender(senderOptions)
        }


    }

}
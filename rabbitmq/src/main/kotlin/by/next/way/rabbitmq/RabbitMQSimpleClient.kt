package by.next.way.rabbitmq

import com.google.gson.Gson
import com.rabbitmq.client.*
import org.apache.logging.log4j.LogManager


class RabbitMQSimpleClient(
        private val exchange: String,
        private val queue: String,
        private val key: String,
        private val userName: String = "guest",
        private val password: String = "guest",
        private val host: String = "localhost",
        private val connectionFactory: ConnectionFactory = initSender(
                host = host,
                queue = queue,
                key = key,
                exchange = exchange,
                userName = userName,
                password = password
        ),
        private val gson: Gson = Gson()
) {

    fun send(data: Any): Boolean {
        val properties = AMQP.BasicProperties.Builder()
                .type(key)
                .contentType("application/json")
                .deliveryMode(2)
                .build()
        val json = gson.toJson(data)
        log.info("Send to $key: $json")
        return try {
            connectionFactory.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    channel.basicPublish(exchange, key,
                            properties,
                            json.toByteArray())
                }
            }
            true
        } catch (e: Exception) {
            log.error("Send error", e)
            false
        }
    }

    fun simpleRead(): String? {
        val connection = connectionFactory.newConnection()
        val channel = connection.createChannel()
        val autoAck = false
        val response: GetResponse? = channel.basicGet(queue, autoAck);
        val result = if (response == null) {
            log.warn("No messages")
            null
        } else {
            val body = response.body
            val message = String(body)
            log.info(" [x] Received '$message'")
            val deliveryTag = response.envelope.deliveryTag
            channel.basicAck(deliveryTag, false); // acknowledge receipt of the message
            message
        }
        channel.close()
        connection.close()
        return result
    }

    fun read(deliverCallback: DeliverCallback = DeliverCallback { _, message ->
        log.info(" [x] Received '${String(message.body)}'")
    }, cancelCallback: CancelCallback = CancelCallback {
        log.warn("CancelCallback: $it")
    }) {
        val connection = connectionFactory.newConnection()
        val channel = connection.createChannel()
        channel.basicConsume(queue, deliverCallback, cancelCallback)
    }


    companion object {
        private val log = LogManager.getLogger()

        fun initSender(host: String, exchange: String, queue: String, key: String, userName: String, password: String): ConnectionFactory {
            val factory = ConnectionFactory()
            factory.username = userName
            factory.password = password
            factory.host = host
            factory.useNio()
            factory.newConnection().use { connection ->
                connection.createChannel().use { channel ->
                    channel.exchangeDeclare(exchange, "direct", true)
                    channel.queueDeclare(queue, true, false, false, null)
                    channel.queueBind(queue, exchange, key)
                }
            }
            return factory
        }

    }

}
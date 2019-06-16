package by.next.way.rsocket.kotlin

import org.springframework.boot.SpringApplication

object SpringRsocketApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SpringRsocketConfig::class.java, *args)
    }
}


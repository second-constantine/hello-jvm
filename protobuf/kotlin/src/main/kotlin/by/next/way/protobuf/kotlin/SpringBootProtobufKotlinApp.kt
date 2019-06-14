package by.next.way.protobuf.kotlin

import org.springframework.boot.SpringApplication

object SpringBootProtobufKotlinApp {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SpringBootProtobufKotlinConfig::class.java, *args)
    }
}
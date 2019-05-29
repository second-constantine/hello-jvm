package by.next.way.spring.boot.kotlin

import org.springframework.boot.SpringApplication

object SpringBootGrpsKotlinApp {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SpringBootGrpsKotlinConfig::class.java, *args)
    }
}
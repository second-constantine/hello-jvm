package by.next.way.spring.boot.kotlin

import org.springframework.boot.SpringApplication

object SpringBootKotlinApp {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SpringBootKotlinConfig::class.java, *args)
    }
}
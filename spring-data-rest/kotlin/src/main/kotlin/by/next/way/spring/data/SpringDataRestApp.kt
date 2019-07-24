package by.next.way.spring.data

import by.next.way.spring.data.config.SpringDataMvcConfig
import org.springframework.boot.SpringApplication


object SpringDataRestApp {

    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(SpringDataMvcConfig::class.java, *args)
    }
}

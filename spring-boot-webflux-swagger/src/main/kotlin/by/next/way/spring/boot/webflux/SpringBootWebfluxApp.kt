package by.next.way.spring.boot.webflux

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class SpringBootWebfluxApp {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val application = SpringApplication(SpringBootWebfluxApp::class.java)
            application.webApplicationType = WebApplicationType.REACTIVE
            application.run(*args)
        }
    }
}
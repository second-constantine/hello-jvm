package by.next.way.spring.data.config

import by.next.way.spring.data.events.AuthorEventHandler
import by.next.way.spring.data.events.BookEventHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@ComponentScan("by.next.way.spring.data")
@Configuration
@EnableWebMvc
class SpringDataMvcConfig : WebMvcConfigurer {

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        configurer.enable()
    }

    @Bean
    internal fun authorEventHandler(): AuthorEventHandler {
        return AuthorEventHandler()
    }

    @Bean
    internal fun bookEventHandler(): BookEventHandler {
        return BookEventHandler()
    }

}

package by.next.way.spring.boot.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:spring-boot-kotlin.properties")
@ComponentScan("by.next.way.spring.boot.kotlin")
class SpringBootKotlinConfig {

}
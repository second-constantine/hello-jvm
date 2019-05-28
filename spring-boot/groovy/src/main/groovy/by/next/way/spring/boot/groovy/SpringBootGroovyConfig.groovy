package by.next.way.spring.boot.groovy

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:spring-boot-groovy.properties")
@ComponentScan("by.next.way.spring.boot.groovy")
class SpringBootGroovyConfig {
}

package by.next.way.spring.boot.scala

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{ComponentScan, PropertySource}

@SpringBootApplication
@PropertySource(Array("classpath:spring-boot-scala.properties"))
@ComponentScan(Array("by.next.way.spring.boot.scala"))
class SpringBootScalaConfig

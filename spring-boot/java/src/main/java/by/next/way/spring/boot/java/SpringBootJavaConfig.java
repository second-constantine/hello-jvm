package by.next.way.spring.boot.java;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:spring-boot-java.properties")
@ComponentScan("by.next.way.spring.boot.java")
@Configuration
public class SpringBootJavaConfig {
}

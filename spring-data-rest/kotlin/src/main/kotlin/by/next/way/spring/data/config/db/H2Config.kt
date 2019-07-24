package by.next.way.spring.data.config.db

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource


@Configuration
@Profile("h2")
@PropertySource("classpath:persistence-h2.properties")
internal class H2Config

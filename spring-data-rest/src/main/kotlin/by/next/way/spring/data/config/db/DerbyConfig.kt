package by.next.way.spring.data.config.db

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource


@Configuration
@Profile("derby")
@PropertySource("classpath:persistence-derby.properties")
internal class DerbyConfig
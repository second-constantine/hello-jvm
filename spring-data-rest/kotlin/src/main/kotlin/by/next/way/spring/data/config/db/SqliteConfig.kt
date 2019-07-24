package by.next.way.spring.data.config.db

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

@Configuration
@Profile("sqlite")
@PropertySource("classpath:persistence-sqlite.properties")
internal class SqliteConfig

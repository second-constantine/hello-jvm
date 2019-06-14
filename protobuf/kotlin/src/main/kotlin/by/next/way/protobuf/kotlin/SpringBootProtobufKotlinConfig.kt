package by.next.way.protobuf.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.PropertySource
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter


@SpringBootApplication
@PropertySource("classpath:spring-boot-kotlin.properties")
@ComponentScan("by.next.way.protobuf.kotlin")
class SpringBootProtobufKotlinConfig {

    @Bean
    internal fun protobufHttpMessageConverter(): ProtobufHttpMessageConverter {
        return ProtobufHttpMessageConverter()
    }
}
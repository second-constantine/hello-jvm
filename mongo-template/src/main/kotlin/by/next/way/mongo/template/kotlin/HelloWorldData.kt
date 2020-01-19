package by.next.way.mongo.template.kotlin

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class HelloWorldData(
        val id: String = "",
        val name: String = "",
        val number: Int = 0,
        val success: Boolean = true,
        val time: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
)
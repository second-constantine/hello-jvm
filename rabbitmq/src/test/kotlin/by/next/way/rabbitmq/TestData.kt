package by.next.way.rabbitmq

import java.util.*

data class TestData (
        val requestUUID: String = UUID.randomUUID().toString(),
        val text: String = ""
)
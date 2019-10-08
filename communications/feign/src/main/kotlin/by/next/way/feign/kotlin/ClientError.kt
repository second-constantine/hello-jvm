package by.next.way.feign.kotlin

data class ClientError(
        override val message: String? = null
) : RuntimeException(message)
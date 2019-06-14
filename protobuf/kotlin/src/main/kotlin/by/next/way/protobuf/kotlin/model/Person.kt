package by.next.way.protobuf.kotlin.model

import java.util.*

data class Person(
        val firstName: String? = null,
        val lastName: String? = null,
        val emailAddress: String? = null,
        val homeAddress: String? = null,
        val phoneNumbers: List<PhoneNumber> = ArrayList<PhoneNumber>()
)
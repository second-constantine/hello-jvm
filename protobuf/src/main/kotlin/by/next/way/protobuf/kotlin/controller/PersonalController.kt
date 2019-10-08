package by.next.way.protobuf.kotlin.controller

import by.next.way.protobuf.kotlin.PersonProto
import by.next.way.protobuf.kotlin.model.Person
import by.next.way.protobuf.kotlin.model.PhoneNumber
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class PersonalController {

    @RequestMapping("json")
    fun getPerson() = Person(
            firstName = "Jake",
            lastName = "Partusch",
            emailAddress = "jakepartusch@abc.com",
            homeAddress = "123 Seasame Street",
            phoneNumbers = arrayListOf(
                    PhoneNumber(areaCode = 123, phoneNumber = 1234567)
            ))


    @RequestMapping(value = ["proto_buf"], produces = ["application/x-protobuf"])
    fun getPersonProto() = PersonProto
            .newBuilder()
            .setFirstName("Jake")
            .setLastName("Partusch")
            .setEmailAddress("jakepartusch@abc.com")
            .setHomeAddress("123 Seasame Street")
            .addPhoneNumbers(PersonProto.PhoneNumber
                    .newBuilder()
                    .setAreaCode(123)
                    .setPhoneNumber(1234567))
            .build()
}
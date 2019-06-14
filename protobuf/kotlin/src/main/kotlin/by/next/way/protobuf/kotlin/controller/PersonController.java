package by.next.way.protobuf.kotlin.controller;

import by.next.way.protobuf.kotlin.PersonProto;
import by.next.way.protobuf.kotlin.model.Person;
import by.next.way.protobuf.kotlin.model.PhoneNumber;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class PersonController {

    @RequestMapping("json")
    public Person getPerson() {
        Person person = new Person("Jake", "Partusch");
        person.setEmailAddress("jakepartusch@abc.com");
        person.setHomeAddress("123 Seasame Street");
        person.addPhoneNumber(new PhoneNumber(123, 1234567));
        return person;
    }

    @RequestMapping(value = "proto_buf", produces = "application/x-protobuf")
    public PersonProto getPersonProto() {
        return PersonProto
                .newBuilder()
                .setFirstName("Jake")
                .setLastName("Partusch")
                .setEmailAddress("jakepartusch@abc.com")
                .setHomeAddress("123 Seasame Street")
                .addPhoneNumbers(PersonProto.PhoneNumber
                        .newBuilder()
                        .setAreaCode(123)
                        .setPhoneNumber(1234567))
                .build();
    }
}

package by.next.way.spring.data.models

import org.springframework.data.rest.core.annotation.RestResource

import javax.persistence.*

@Entity
class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column
    var name: String? = null

    @OneToOne
    @JoinColumn(name = "address_id")
    @RestResource(path = "libraryAddress")
    var address: Address? = null

    @OneToMany(mappedBy = "library")
    var books: List<Book>? = null

    constructor() {}

    constructor(name: String) : super() {
        this.name = name
    }

}

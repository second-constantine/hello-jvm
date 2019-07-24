package by.next.way.spring.data.models

import javax.persistence.*

@Entity
class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var location: String? = null

    @OneToOne(mappedBy = "address")
    var library: Library? = null

    constructor() {}

    constructor(location: String) : super() {
        this.location = location
    }
}

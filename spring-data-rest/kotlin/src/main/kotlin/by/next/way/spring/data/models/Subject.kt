package by.next.way.spring.data.models

import javax.persistence.*

@Entity
class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var name: String? = null
}
package by.next.way.spring.data.models

import javax.persistence.*

@Entity
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var title: String? = null

    var isbn: String? = null

    @ManyToOne
    @JoinColumn(name = "library_id")
    var library: Library? = null

    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    var authors: List<Author>? = null

    constructor() {}

    constructor(title: String) : super() {
        this.title = title
    }

}

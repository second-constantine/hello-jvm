package by.next.way.spring.data.models

import javax.persistence.*

@Entity
class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(nullable = false)
    var name: String? = null

    @ManyToMany(cascade = [CascadeType.ALL])
    @JoinTable(name = "book_author", joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")])
    var books: List<Book>? = null

    constructor() {}

    constructor(name: String) : super() {
        this.name = name
    }
}

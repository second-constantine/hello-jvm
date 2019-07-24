package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository : CrudRepository<Author, Long>

package by.next.way.spring.data.repositories

import by.next.way.spring.data.models.Book
import by.next.way.spring.data.projections.CustomBook
import org.springframework.data.repository.CrudRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(excerptProjection = CustomBook::class)
interface BookRepository : CrudRepository<Book, Long>

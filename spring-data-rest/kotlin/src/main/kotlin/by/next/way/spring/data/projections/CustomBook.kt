package by.next.way.spring.data.projections

import by.next.way.spring.data.models.Author
import by.next.way.spring.data.models.Book
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.rest.core.config.Projection

@Projection(name = "customBook", types = [Book::class])
interface CustomBook {
    @get:Value("#{target.id}")
    val id: Long

    val title: String

    val authors: List<Author>

    @get:Value("#{target.getAuthors().size()}")
    val authorCount: Int
}

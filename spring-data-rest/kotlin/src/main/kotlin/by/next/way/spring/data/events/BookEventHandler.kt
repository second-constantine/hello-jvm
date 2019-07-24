package by.next.way.spring.data.events

import by.next.way.spring.data.models.Author
import by.next.way.spring.data.models.Book
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler

import java.util.logging.Logger

@RepositoryEventHandler
class BookEventHandler {
    internal var logger = Logger.getLogger("Class BookEventHandler")

    @HandleBeforeCreate
    fun handleBookBeforeCreate(book: Book) {

        logger.info("Inside Book Before Create ....")
        book.authors
    }

    @HandleBeforeCreate
    fun handleAuthorBeforeCreate(author: Author) {
        logger.info("Inside Author Before Create ....")
        author.books
    }
}

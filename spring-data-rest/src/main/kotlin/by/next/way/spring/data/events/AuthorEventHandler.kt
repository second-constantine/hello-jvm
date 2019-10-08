package by.next.way.spring.data.events

import by.next.way.spring.data.models.Author
import org.springframework.data.rest.core.annotation.*

import java.util.logging.Logger

@RepositoryEventHandler
class AuthorEventHandler {
    internal var logger = Logger.getLogger("Class AuthorEventHandler")

    @HandleBeforeCreate
    fun handleAuthorBeforeCreate(author: Author) {
        logger.info("Inside  Author Before Create....")
        val name = author.name
    }

    @HandleAfterCreate
    fun handleAuthorAfterCreate(author: Author) {
        logger.info("Inside  Author After Create ....")
        val name = author.name
    }

    @HandleBeforeDelete
    fun handleAuthorBeforeDelete(author: Author) {
        logger.info("Inside  Author Before Delete ....")
        val name = author.name
    }

    @HandleAfterDelete
    fun handleAuthorAfterDelete(author: Author) {
        logger.info("Inside  Author After Delete ....")
        val name = author.name
    }

}

package by.next.way.spring.data.events


import by.next.way.spring.data.models.Author
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class AuthorEventHandlerUnitTest {

    @Test
    fun whenCreateAuthorThenSuccess() {
        val author = mock(Author::class.java)
        val authorEventHandler = AuthorEventHandler()
        authorEventHandler.handleAuthorBeforeCreate(author)
        Mockito.verify(author, Mockito.times(1)).name

    }

    @Test
    fun whenDeleteAuthorThenSuccess() {
        val author = mock(Author::class.java)
        val authorEventHandler = AuthorEventHandler()
        authorEventHandler.handleAuthorAfterDelete(author)
        Mockito.verify(author, Mockito.times(1)).name

    }
}
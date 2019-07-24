package by.next.way.spring.data.events

import by.next.way.spring.data.models.Author
import by.next.way.spring.data.models.Book
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class BookEventHandlerUnitTest {
    @Test
    fun whenCreateBookThenSuccess() {
        val book = mock(Book::class.java)
        val bookEventHandler = BookEventHandler()
        bookEventHandler.handleBookBeforeCreate(book)
        Mockito.verify(book, Mockito.times(1)).authors

    }

    @Test
    fun whenCreateAuthorThenSuccess() {
        val author = mock(Author::class.java)
        val bookEventHandler = BookEventHandler()
        bookEventHandler.handleAuthorBeforeCreate(author)
        Mockito.verify(author, Mockito.times(1)).books

    }
}
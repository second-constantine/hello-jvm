package by.next.way.spring.data.projections

import by.next.way.spring.data.config.SpringDataMvcConfig
import by.next.way.spring.data.models.Author
import by.next.way.spring.data.models.Book
import by.next.way.spring.data.repositories.AuthorRepository
import by.next.way.spring.data.repositories.BookRepository
import io.restassured.RestAssured
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SpringDataMvcConfig::class], webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpringDataProjectionLiveTest {

    @Autowired
    private val bookRepo: BookRepository? = null

    @Autowired
    private val authorRepo: AuthorRepository? = null

    @Before
    fun setup() {
        if (!bookRepo!!.findById(1L).isPresent()) {
            var book = Book("Animal Farm")
            book.isbn = "978-1943138425"
            book = bookRepo.save(book)
            var author = Author("George Orwell")
            author = authorRepo!!.save(author)
            author.books = arrayListOf(book)
            author = authorRepo.save(author)
        }
    }

    @Test
    fun whenGetBook_thenOK() {
        val response = RestAssured.get("$BOOK_ENDPOINT/1")

        assertEquals(200, response.statusCode.toLong())
        assertTrue(response.asString().contains("isbn"))
        assertFalse(response.asString().contains("authorCount"))
        // System.out.println(response.asString());
    }

    @Test
    fun whenGetBookProjection_thenOK() {
        val response = RestAssured.get("$BOOK_ENDPOINT/1?projection=customBook")

        assertEquals(200, response.statusCode.toLong())
        assertFalse(response.asString().contains("isbn"))
        assertTrue(response.asString().contains("authorCount"))
        // System.out.println(response.asString());
    }

    @Test
    fun whenGetAllBooks_thenOK() {
        val response = RestAssured.get(BOOK_ENDPOINT)

        assertEquals(200, response.statusCode.toLong())
        assertFalse(response.asString().contains("isbn"))
        assertTrue(response.asString().contains("authorCount"))
        // System.out.println(response.asString());
    }

    @Test
    fun whenGetAuthorBooks_thenOK() {
        val response = RestAssured.get("$AUTHOR_ENDPOINT/1/books")

        assertEquals(200, response.statusCode.toLong())
        assertFalse(response.asString().contains("isbn"))
        assertTrue(response.asString().contains("authorCount"))
        println(response.asString())
    }

    companion object {
        private val BOOK_ENDPOINT = "http://localhost:8080/books"
        private val AUTHOR_ENDPOINT = "http://localhost:8080/authors"
    }
}

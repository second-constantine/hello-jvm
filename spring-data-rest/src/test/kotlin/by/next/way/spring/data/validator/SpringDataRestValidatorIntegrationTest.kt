package by.next.way.spring.data.validator

import by.next.way.spring.data.config.SpringDataMvcConfig
import by.next.way.spring.data.models.WebsiteUser
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [SpringDataMvcConfig::class], webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SpringDataRestValidatorIntegrationTest {

    @Autowired
    private var mockMvc: MockMvc? = null

    @Autowired
    protected var wac: WebApplicationContext? = null

    @Before
    fun setup() {
        mockMvc = webAppContextSetup(wac!!).build()
    }

    @Test
    @Throws(Exception::class)
    fun whenStartingApplication_thenCorrectStatusCode() {
        mockMvc!!.perform(get("/users")).andExpect(status().is2xxSuccessful())
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Throws(Exception::class)
    fun whenAddingNewCorrectUser_thenCorrectStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"
        user.name = "John Doe"

        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful)
                .andExpect(redirectedUrl("http://localhost/users/1"))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithoutName_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"

        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotAcceptable())
                .andExpect(redirectedUrl(null))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithEmptyName_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"
        user.name = ""
        mockMvc!!.perform(post("/users", user)
                .contentType(MediaType.APPLICATION_JSON).content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotAcceptable)
                .andExpect(redirectedUrl(null))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithoutEmail_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.name = "John Doe"

        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotAcceptable()).andExpect(redirectedUrl(null))
    }

    @Test
    @Throws(Exception::class)
    fun whenAddingNewUserWithEmptyEmail_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.name = "John Doe"
        user.email = ""
        mockMvc!!.perform(post("/users", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotAcceptable)
                .andExpect(redirectedUrl(null))
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Throws(Exception::class)
    fun whenDeletingCorrectUser_thenCorrectStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"
        user.name = "John Doe"
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/users/1"))
        mockMvc!!.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isMethodNotAllowed())
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Throws(Exception::class)
    fun whenSearchingByEmail_thenCorrectStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"
        user.name = "John Doe"
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/users/1"))
        mockMvc!!.perform(get("/users/search/byEmail").param("email", user.email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
    }

    @Test
    @Throws(Exception::class)
    fun whenSearchingByEmailWithOriginalMethodName_thenErrorStatusCodeAndResponse() {
        val user = WebsiteUser()
        user.email = "john.doe@john.com"
        user.name = "John Doe"
        mockMvc!!.perform(post("/users", user).contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(redirectedUrl("http://localhost/users/1"))
        mockMvc!!.perform(get("/users/search/findByEmail").param("email", user.email)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
    }

    companion object {
        val URL = "http://localhost"
    }
}

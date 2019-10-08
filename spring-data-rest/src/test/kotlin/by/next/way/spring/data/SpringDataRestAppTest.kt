package by.next.way.spring.data

import by.next.way.spring.data.config.SpringDataMvcConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [SpringDataMvcConfig::class])
class SpringDataRestAppTest {

    @Test
    fun whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}

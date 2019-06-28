package by.next.way.retrofit.kotlin

import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Disabled
class RetrofitConnectorTest {

    private val retrofitConnector = Retrofit.Builder()
            .baseUrl("https://postman-echo.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create()) //FOR JSON
            .build()
            .create(RetrofitService::class.java)

    @Test
    fun get() {
        val result = runBlocking {
            retrofitConnector.get()
        }
        val response = result.body()
        log.info(response)
        Assertions.assertNotNull(response)
    }

    @Test
    fun post() {
        val result = runBlocking {
            retrofitConnector.post("hello world!")
        }
        val response = result.body()
        log.info(response)
        Assertions.assertNotNull(response)
        Assertions.assertTrue(response!!.contains("hello world!"))
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}
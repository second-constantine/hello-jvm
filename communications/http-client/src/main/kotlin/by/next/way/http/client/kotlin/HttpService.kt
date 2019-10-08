package by.next.way.http.client.kotlin

import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import org.apache.logging.log4j.LogManager
import java.io.IOException

class HttpService(
        private val client: CloseableHttpClient = HttpClientBuilder.create().build()
) {
    fun uploadDocument(url: String, multipart: MultipartEntityBuilder) = try {
        val request = HttpPost(url)
        request.entity = multipart.build()
        val response = client.execute(request)
        val result = EntityUtils.toString(response?.entity)
        log.info(result)
        result ?: ""
    } catch (e: IOException) {
        log.error("file uploaded error", e)
        ""
    }

    fun sendPost(url: String, json: String) = try {
        val request = HttpPost(url)
        request.entity = StringEntity(json, "utf-8")
        request.setHeader("Content-type", "application/json; charset=utf-8")
        val response = client.execute(request)
        val result = EntityUtils.toString(response?.entity)
        log.info(result)
        result ?: ""
    } catch (e: IOException) {
        log.error(json, e
        )
        ""
    }

    fun sendGet(url: String) = try {
        val request = HttpGet(url)
        val response = client.execute(request)
        val result = EntityUtils.toString(response?.entity)
        log.info(result)
        result ?: ""
    } catch (e: IOException) {
        log.error(url, e)
        ""
    }

    companion object {
        private val log = LogManager.getLogger()
    }
}
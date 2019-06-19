package by.next.way.aws.kotlin

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.*
import java.util.stream.Collectors

/**
 * Handler for requests to Lambda function.
 */
open class AwsLambda : RequestHandler<Any, Any> {

    override fun handleRequest(input: Any?, context: Context?): Any {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["X-Custom-Header"] = "application/json"
        try {
            val pageContents = this.getPageContents("https://checkip.amazonaws.com")
            val output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents)
            return GatewayResponse(output, headers, 200)
        } catch (e: IOException) {
            return GatewayResponse("{}", headers, 500)
        }

    }

    @Throws(IOException::class)
    private fun getPageContents(address: String): String {
        val url = URL(address)
        BufferedReader(InputStreamReader(url.openStream())).use { br -> return br.lines().collect(Collectors.joining(System.lineSeparator())) }
    }
}